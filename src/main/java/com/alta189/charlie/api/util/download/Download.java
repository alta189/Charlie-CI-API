/*
 * This file is part of Charlie CI API.
 *
 * Copyright (c) ${project.inceptionYear}, Stephen Williams (alta189) <http://charlie.alta189.com/>
 * Charlie CI API is licensed under the GNU Lesser General Public License.
 *
 * Charlie CI API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Charlie CI API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alta189.charlie.api.util.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ReadableByteChannel;

import com.alta189.charlie.api.Constants;

/**
 * <p>
 *     Asynchronous file download
 * </p>
 * <p>
 *     No external libraries may be used in this file
 * </p>
 */
public class Download implements Runnable, ProgressListener {
	private final URL url;
	private final File output;
	private long size = -1;
	private DownloadListener listener;
	private DownloadResult result = DownloadResult.FAILURE;
	private Exception exception = null;

	public Download(String url, File output) throws MalformedURLException {
		this.url = new URL(url);
		this.output = output;
	}

	@Override
	public void run() {
		ReadableByteChannel rbc = null;
		FileOutputStream fos = null;

		try {

			URLConnection conn = url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(false);
			System.setProperty("http.agent", Constants.Download.USER_AGENT);
			conn.setRequestProperty("User-Agent", Constants.Download.USER_AGENT);
			HttpURLConnection.setFollowRedirects(true);
			conn.setUseCaches(false);
			((HttpURLConnection)conn).setInstanceFollowRedirects(true);
			conn.setConnectTimeout(Constants.Download.CONNECT_TIMEOUT);
			conn.setReadTimeout(Constants.Download.READ_TIMEOUT);

			conn.connect();
			InputStream in = conn.getInputStream();

			size = conn.getContentLength();
			output.delete();

			rbc = new ReadableByteChannelWrapper(Channels.newChannel(in), size, this);
			fos = new FileOutputStream(output);

			progress(0);
			fos.getChannel().transferFrom(rbc, 0, size > 0 ? size : Integer.MAX_VALUE);
			in.close();
			rbc.close();
			if (size > 0) {
				if (size == output.length()) {
					result = DownloadResult.SUCCESS;
				}
			} else {
				result = DownloadResult.SUCCESS;
			}
		} catch (ClosedByInterruptException e) {
			result = DownloadResult.INTERRUPTED;
			exception = e;
		} catch (Exception e) {
			exception = e;
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ignored) {
				}
			}
			if (rbc != null) {
				try {
					rbc.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	public static class ReadableByteChannelWrapper implements ReadableByteChannel {
		private final ReadableByteChannel wrapped;
		private final long size;
		private final ProgressListener callback;
		private long read;

		public ReadableByteChannelWrapper(ReadableByteChannel wrapped, long size, ProgressListener callback) {
			this.wrapped = wrapped;
			this.size = size;
			this.callback = callback;
		}

		public boolean isOpen() {
			return wrapped.isOpen();
		}

		public void close() throws IOException {
			wrapped.close();
		}

		public int read(ByteBuffer dst) throws IOException {
			int num = wrapped.read(dst);
			if (num > 0) {
				read += num;
				callback.progress(read / (float) size);
			}
			return num;
		}
	}

	@Override
	public void progress(float progress) {
		if (listener != null) {
			listener.stateChanged(output, progress);
		}
	}

	public void setListener(DownloadListener listener) {
		this.listener = listener;
	}

	public DownloadResult getResult() {
		return result;
	}

	public File getOutput() {
		return output;
	}

	public Exception getException() {
		return exception;
	}
}
