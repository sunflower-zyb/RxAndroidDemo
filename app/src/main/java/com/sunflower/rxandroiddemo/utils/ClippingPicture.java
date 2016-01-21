package com.sunflower.rxandroiddemo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片剪切
 *
 * @author zhangyb@ifenguo.com
 * @createDate 2015年3月10日
 */
public class ClippingPicture {

    /**
     * 保存图片到文�?
     *
     * @param path
     * @param data
     */
    public static void savePicture(String path, byte[] data) {
        if (data == null) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        File f = new File(path);
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(f));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片到文�?
     *
     * @param path
     * @param bitmap
     */
    public static void savePicture(String path, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        File f = new File(path);
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(f));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从asset目录读取图片
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Bitmap getImageFromAssetsDef(Context context, String fileName) {
        return getImageFromAssetsDef(context.getResources(), fileName);
    }

    public static Bitmap getImageFromAssetsDef(Resources resource, String fileName) {
        if (fileName == null) {
            return null;
        }
        Bitmap image = null;
        AssetManager am = resource.getAssets();
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            InputStream is = am.open(fileName);
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inPreferredConfig = Config.RGB_565;
            image = BitmapFactory.decodeStream(is, null, options);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 图片读取
     *
     * @param desc
     * @return
     */
    public static Bitmap decodeBitmap(FileDescriptor desc) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        try {
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            opts.inPreferredConfig = Config.RGB_565;
            Bitmap bmp = BitmapFactory.decodeFileDescriptor(desc, null, opts);
            return bmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据路径从sd卡读取图�?
     *
     * @param filePath
     * @return
     */
    public static Bitmap decodeBitmapSd(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            FileDescriptor fileDescriptor = inputStream.getFD();
            bitmap = decodeBitmap(fileDescriptor);
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("FileNotFound", filePath + "not found");
        } catch (IOException e) {
            Log.e("IOException", filePath + "read error");
        }
        return bitmap;
    }

    /**
     * 按照指定的宽高成比例的读取缩小的图片
     *
     * @param filePath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap decodeResizeBitmapSd(String filePath, int width, int height) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            FileDescriptor fileDescriptor = inputStream.getFD();
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 1;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Config.RGB_565;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, opts);
            // 计算缩放�?
            int simpleSize = calculateInSampleSize(opts, width, height);
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = simpleSize;
            // 正式读取图片
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, opts);
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("FileNotFound", filePath + "not found");
        } catch (IOException e) {
            Log.e("IOException", filePath + "read error");
        }
        return bitmap;
    }

    /**
     * 精确缩放图片到某个尺�?
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    public static Bitmap resizeBitmap(String path, int width, int height) {
        Bitmap bitmap = decodeResizeBitmapSd(path, width, height);
        int bitmapWidth = bitmap.getWidth();
        float scale = width / (bitmapWidth * 1.0f);
        if (scale > 1) {
            return bitmap;
        }
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        if (bitmap != newbm) {
            bitmap.recycle();
        }
        return newbm;
    }

    /**
     * 修改图片为圆�?
     *
     * @param bitmap
     * @return
     */
    public static Bitmap toCircleCorner(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap output = Bitmap
                .createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = bitmap.getWidth() / 2;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        if (output != bitmap) {
            bitmap.recycle();
        }
        return output;
    }

    /**
     * 裁剪图片为圆�?
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        if (bitmap == null) {
            return null;
        }
        Bitmap output = Bitmap
                .createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getHeight(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels * bitmap.getWidth() / 128;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        if (output != bitmap) {
            bitmap.recycle();
        }
        return output;
    }

    /**
     * 从resource目录读取图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawableFromResource(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }

    /**
     * 根据宽高计算缩放�?
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            final float totalPixels = width * height;
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    /**
     * bitmap转为byte[]
     *
     * @param bitmap
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

//    public static String bitmapToBase64(String file) {
//        byte[] iconByte = FileUtil.readFile(file);
//        String total = Base64.encodeToString(iconByte, Base64.DEFAULT);
//        return total;
//    }

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Bitmap decodeBitmapStream(InputStream input, int width, int height) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Config.RGB_565;
        // BitmapFactory.decodeStream(input, null, opts);
        // 计算缩放�?
        int simpleSize = calculateInSampleSize(opts, width, height);
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = simpleSize;
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, opts);
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static Bitmap decodeBitmapResourse(Context context, int resourceId) {
        Resources resource = context.getResources();
        return BitmapFactory.decodeResource(resource, resourceId);
    }

    /**
     * 读取图片属性：旋转的角度 三星手机特别注意
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片，使图片保持正确的方向。
     *
     * @param bitmap  原始图片
     * @param degrees 原始图片的角度
     * @return Bitmap 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return bmp;
    }

    /**
     * @param @param  view
     * @param @return 设定文件
     * @return Bitmap 返回类型
     * @throws
     * @Title: convertViewToBitmap
     * @Description: View转换为Bitmap
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public static byte[] convertViewToByte(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.destroyDrawingCache();
        return baos.toByteArray();
    }
}
