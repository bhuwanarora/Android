//package com.csform.android.uiapptemplate.util;
//
//
//import android.os.AsyncTask;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//
//public class ImageDownloadAsyncTask extends AsyncTask {
//    public interface ImageDownloadListener {
//        public void onImageDownloadComplete(byte[] bitmapData);
//        public void onImageDownloadFailed(Exception ex);
//    }
//
//    private ImageDownloadListener mListener = null;
//
//    public ImageDownloadAsyncTask(ImageDownloadListener listener) {
//        mListener = listener;
//    }
//
//    protected Object doInBackground(Object... urls) {
//        String url = (String)urls[0];
//        ByteArrayOutputStream baos = null;
//        InputStream mIn = null;
//        try {
//            mIn = new java.net.URL(url).openStream();
//            int bytesRead;
//            byte[] buffer = new byte[64];
//            baos = new ByteArrayOutputStream();
//            while ((bytesRead = mIn.read(buffer)) > 0) {
//                if (isCancelled()) return null;
//                baos.write(buffer, 0, bytesRead);
//            }
//            return new AsyncTaskResult<byte[]>(baos.toByteArray());
//
//        } catch (Exception ex) {
//            return new AsyncTaskResult<byte[]>(ex);
//        }
//        finally {
//            Quick.close(mIn);
//            Quick.close(baos);
//        }
//    }
//
//    protected void onPostExecute(Object objResult) {
//        AsyncTaskResult<byte[]> result = (AsyncTaskResult<byte[]>)objResult;
//        if (isCancelled() || result == null) return;
//        if (result.getError() != null) {
//            mListener.onImageDownloadFailed(result.getError());
//        }
//        else if (mListener != null)
//            mListener.onImageDownloadComplete(result.getResult());
//    }
//}