package kz.post.jcourier.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import kz.post.jcourier.BuildConfig;
import kz.post.jcourier.R;


/**
 * Utility methods for working with the filesystem.
 */
public class FileUtils {

    /**
     * Given a Uri returned by an activity triggered using the ACTION_GET_CONTENT intent,
     * return the absolute filesystem path corresponding to it.
     *
     * @param context - any context.
     * @param uri     - the image URI.
     * @return An absolute path to the image.
     */
    @SuppressLint("NewApi")
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + File.separator + split[1];
                } else {
                    return copyUriIntoCacheAndReturnPath(context, uri);
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.parseLong(docId));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                switch (type) {
                    case "image":
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "video":
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "audio":
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        break;
                }

                final String selection = "_id = ?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            } else {
                return copyUriIntoCacheAndReturnPath(context, uri);
            }
        }
        // MediaStore (and general).
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            } else {
                return copyUriIntoCacheAndReturnPath(context, uri);
            }
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String result = null;
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                result = cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public static String getDisplayName(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME);
                return cursor.getString(nameIndex);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return null;
    }

    private static String copyUriIntoCacheAndReturnPath(Context context, Uri uri) {
        try {
            // Open the damn input stream, copy into a local cache file, return path to local cache file.
            FileInputStream inputStream = (FileInputStream) context.getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                String fileName = getDisplayName(context, uri);

                File cacheFile = createCacheFile(context, fileName);
                FileOutputStream outputStream = new FileOutputStream(cacheFile);

                int n = 0;
                byte[] buffer = new byte[8192];
                while (n != -1) {
                    n = inputStream.read(buffer);
                    if (n > 0) {
                        outputStream.write(buffer, 0, n);
                    }
                }

                return cacheFile.getAbsolutePath();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static Uri getContentUriForFile(Context context, File file) {
        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        return Uri.parse(uri.getScheme() + "://" + uri.getAuthority() + uri.getPath());
    }

    /**
     * Create a private temporary image file.
     *
     * @param context - any context.
     * @return The File object.
     * @throws IOException If could not create the temporary file.
     */
    public static File createImageFile(Context context) throws IOException {
        File directory = context.getCacheDir();
        return File.createTempFile(getTempFileNamePrefix("img"), ".jpg", directory);
    }

    /**
     * Create an external (that is, accessible by other apps) temporary image file.
     *
     * @param context Any context.
     * @return The File object.
     * @throws IOException If could not create the file.
     */
    @Nullable
    public static File createExternalImageFile(Context context) throws IOException {
        File directory = context.getExternalFilesDir(null);

        if (directory == null) {
            return null;
        }

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                return null;
            }
        }

        return File.createTempFile(getTempFileNamePrefix("img"), ".jpg", directory);
    }

    /**
     * Create an external (that is, accessible by other apps) temporary video file.
     *
     * @param context Any context.
     * @return The File object.
     * @throws IOException If could not create the file.
     */
    @Nullable
    public static File createExternalVideoFile(Context context) throws IOException {
        File directory = context.getExternalFilesDir(null);

        if (directory == null) {
            return null;
        }

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                return null;
            }
        }

        return File.createTempFile(getTempFileNamePrefix("video"), ".mp4", directory);
    }

    /**
     * Create a new external (that is, accessible by other apps) file with the specified file name.
     *
     * @param context  Any context.
     * @param fileName The name of the file to create.
     * @return The created File.
     */
    public static File createExternalFile(Context context, String fileName) {
        File directory = new File(
                Environment.getExternalStorageDirectory(),
                context.getString(R.string.app_name_jcourier));

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                return null;
            }
        }

        return new File(directory, fileName);
    }

    public static File createCacheFile(Context context, String fileName) throws NullPointerException {
        final File directory = context.getCacheDir();
        return new File(directory, fileName);
    }

    /**
     * Generate a unique file name with the specified type to be set as a prefix.
     *
     * @param type Will be used as a prefix of the prefix.
     * @return File name prefix, without extension.
     */
    private static String getTempFileNamePrefix(String type) {
        return type + "_" + System.currentTimeMillis();
    }

    public static String getMimeType(Context context, Uri uri) {
        ContentResolver cr = context.getContentResolver();
        return cr.getType(uri);
    }

    /**
     * Delete the specified file.
     *
     * @param file The file to delete.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteFile(File file) {
        if (file != null) {
            file.delete();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void clearCache(Context context) {
        File directory = context.getCacheDir();
        String[] files = directory.list();
        if (files == null) return;

        for (String name : files) {
            new File(directory, name).delete();
        }
    }
}
