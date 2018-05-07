package wuxiang.miku.scorpio.paperactivate.modules.home.childpagers.right_ocr;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baidu.idcardquality.IDcardQualityProcess;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraThreadPool;
import com.baidu.ocr.ui.camera.CameraView;
import com.baidu.ocr.ui.camera.ICameraControl;
import com.baidu.ocr.ui.camera.MaskView;
import com.baidu.ocr.ui.camera.OCRCameraLayout;
import com.baidu.ocr.ui.camera.PermissionCallback;
import com.baidu.ocr.ui.crop.CropView;
import com.baidu.ocr.ui.crop.FrameOverlayView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import wuxiang.miku.scorpio.paperactivate.R;
import wuxiang.miku.scorpio.paperactivate.base.BaseFragment;
import wuxiang.miku.scorpio.paperactivate.bean.Note;
import wuxiang.miku.scorpio.paperactivate.bean.OcrGenWords;
import wuxiang.miku.scorpio.paperactivate.utils.RecognizeService;

public class OcrFragment extends BaseFragment {
    public static final String KEY_OUTPUT_FILE_PATH = "outputFilePath";
    public static final String KEY_CONTENT_TYPE = "contentType";
    public static final String KEY_NATIVE_TOKEN = "nativeToken";
    public static final String KEY_NATIVE_ENABLE = "nativeEnable";
    public static final String KEY_NATIVE_MANUAL = "nativeEnableManual";

    public static final String CONTENT_TYPE_GENERAL = "general";
    public static final String CONTENT_TYPE_ID_CARD_FRONT = "IDCardFront";
    public static final String CONTENT_TYPE_ID_CARD_BACK = "IDCardBack";
    public static final String CONTENT_TYPE_BANK_CARD = "bankCard";

    private static final int REQUEST_CODE_PICK_IMAGE = 100;
    private static final int PERMISSIONS_REQUEST_CAMERA = 800;
    private static final int PERMISSIONS_EXTERNAL_STORAGE = 801;

    private File outputFile;
    private String contentType;
    private Handler handler = new Handler();

    private boolean isNativeEnable;
    private boolean isNativeManual;

    private OCRCameraLayout takePictureContainer;
    private OCRCameraLayout cropContainer;
    private OCRCameraLayout confirmResultContainer;
    private ImageView lightButton;
    private CameraView cameraView;
    private ImageView displayImageView;
    private CropView cropView;
    private FrameOverlayView overlayView;
    private MaskView cropMaskView;
    private ImageView takePhotoBtn;
    private ProgressBar mProgressBar;
    private PermissionCallback permissionCallback = new PermissionCallback() {
        @Override
        public boolean onRequestPermission() {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSIONS_REQUEST_CAMERA);
            return false;
        }
    };


    public static OcrFragment newInstance() {
        return new OcrFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.bd_ocr_activity_camera;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initView();
    }


    private void initView() {
        mProgressBar = getActivity().findViewById(R.id.ocr_pro_bar);

        takePictureContainer = (OCRCameraLayout) getActivity().findViewById(com.baidu.ocr.ui.R.id.take_picture_container);
        confirmResultContainer = (OCRCameraLayout) getActivity().findViewById(com.baidu.ocr.ui.R.id.confirm_result_container);

        cameraView = (CameraView) getActivity().findViewById(com.baidu.ocr.ui.R.id.camera_view);
        cameraView.getCameraControl().setPermissionCallback(permissionCallback);
        lightButton = (ImageView) getActivity().findViewById(com.baidu.ocr.ui.R.id.light_button);
        lightButton.setOnClickListener(lightButtonOnClickListener);
        takePhotoBtn = (ImageView) getActivity().findViewById(com.baidu.ocr.ui.R.id.take_photo_button);
        getActivity().findViewById(com.baidu.ocr.ui.R.id.album_button).setOnClickListener(albumButtonOnClickListener);
        takePhotoBtn.setOnClickListener(takeButtonOnClickListener);

        // confirm result;
        displayImageView = (ImageView) getActivity().findViewById(com.baidu.ocr.ui.R.id.display_image_view);
        confirmResultContainer.findViewById(com.baidu.ocr.ui.R.id.confirm_button).setOnClickListener(confirmButtonOnClickListener);
        confirmResultContainer.findViewById(com.baidu.ocr.ui.R.id.cancel_button).setOnClickListener(confirmCancelButtonOnClickListener);
        getActivity().findViewById(com.baidu.ocr.ui.R.id.rotate_button).setOnClickListener(rotateButtonOnClickListener);

        cropView = (CropView) getActivity().findViewById(com.baidu.ocr.ui.R.id.crop_view);
        cropContainer = (OCRCameraLayout) getActivity().findViewById(com.baidu.ocr.ui.R.id.crop_container);
        overlayView = (FrameOverlayView) getActivity().findViewById(com.baidu.ocr.ui.R.id.overlay_view);
        cropContainer.findViewById(com.baidu.ocr.ui.R.id.confirm_button).setOnClickListener(cropConfirmButtonListener);
        cropMaskView = (MaskView) cropContainer.findViewById(com.baidu.ocr.ui.R.id.crop_mask_view);
        cropContainer.findViewById(com.baidu.ocr.ui.R.id.cancel_button).setOnClickListener(cropCancelButtonListener);

        setOrientation(getResources().getConfiguration());
        initParams();

        cameraView.setAutoPictureCallback(autoTakePictureCallback);
    }


    @Override
    public void onStart() {
        super.onStart();
        cameraView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    public void onStop() {
        super.onStop();
        cameraView.stop();
    }

    private void initParams() {
        String outputPath = new File(getActivity().getFilesDir(), "pic.jpg").getAbsolutePath();
//        final String token = getIntent().getStringExtra(KEY_NATIVE_TOKEN);
//        isNativeEnable = getIntent().getBooleanExtra(KEY_NATIVE_ENABLE, true);
//        isNativeManual = getIntent().getBooleanExtra(KEY_NATIVE_MANUAL, false);
        final String token = null;
        isNativeEnable = true;
        isNativeManual = false;

        if (token == null && !isNativeManual) {
            isNativeEnable = false;
        }

        if (outputPath != null) {
            outputFile = new File(outputPath);
        }

//        contentType = getIntent().getStringExtra(KEY_CONTENT_TYPE);
        if (contentType == null) {
            contentType = CONTENT_TYPE_GENERAL;
        }
        int maskType;
        switch (contentType) {
            case CONTENT_TYPE_ID_CARD_FRONT:
                maskType = MaskView.MASK_TYPE_ID_CARD_FRONT;
                overlayView.setVisibility(View.INVISIBLE);
                if (isNativeEnable) {
                    takePhotoBtn.setVisibility(View.INVISIBLE);
                }
                break;
            case CONTENT_TYPE_ID_CARD_BACK:
                maskType = MaskView.MASK_TYPE_ID_CARD_BACK;
                overlayView.setVisibility(View.INVISIBLE);
                if (isNativeEnable) {
                    takePhotoBtn.setVisibility(View.INVISIBLE);
                }
                break;
            case CONTENT_TYPE_BANK_CARD:
                maskType = MaskView.MASK_TYPE_BANK_CARD;
                overlayView.setVisibility(View.INVISIBLE);
                break;
            case CONTENT_TYPE_GENERAL:
            default:
                maskType = MaskView.MASK_TYPE_NONE;
                cropMaskView.setVisibility(View.INVISIBLE);
                break;
        }

        // 身份证本地能力初始化
        if (maskType == MaskView.MASK_TYPE_ID_CARD_FRONT || maskType == MaskView.MASK_TYPE_ID_CARD_BACK) {
            if (isNativeEnable && !isNativeManual) {
                initNative(token);
            }
        }
        cameraView.setEnableScan(isNativeEnable);
        cameraView.setMaskType(maskType, getActivity());
        cropMaskView.setMaskType(maskType);
    }

    private void initNative(final String token) {
        CameraNativeHelper.init(getActivity(), token,
                new CameraNativeHelper.CameraNativeInitCallback() {
                    @Override
                    public void onError(int errorCode, Throwable e) {
                        cameraView.setInitNativeStatus(errorCode);
                    }
                });
    }

    private void showTakePicture() {
        cameraView.getCameraControl().resume();
        updateFlashMode();
        takePictureContainer.setVisibility(View.VISIBLE);
        confirmResultContainer.setVisibility(View.INVISIBLE);
        cropContainer.setVisibility(View.INVISIBLE);
    }

    private void showCrop() {
        cameraView.getCameraControl().pause();
        updateFlashMode();
        takePictureContainer.setVisibility(View.INVISIBLE);
        confirmResultContainer.setVisibility(View.INVISIBLE);
        cropContainer.setVisibility(View.VISIBLE);
    }

    private void showResultConfirm() {
        cameraView.getCameraControl().pause();
        updateFlashMode();
        takePictureContainer.setVisibility(View.INVISIBLE);
        confirmResultContainer.setVisibility(View.VISIBLE);
        cropContainer.setVisibility(View.INVISIBLE);
    }

    // take photo;
    private void updateFlashMode() {
        int flashMode = cameraView.getCameraControl().getFlashMode();
        if (flashMode == ICameraControl.FLASH_MODE_TORCH) {
            lightButton.setImageResource(com.baidu.ocr.ui.R.drawable.bd_ocr_light_on);
        } else {
            lightButton.setImageResource(com.baidu.ocr.ui.R.drawable.bd_ocr_light_off);
        }
    }


    private View.OnClickListener albumButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSIONS_EXTERNAL_STORAGE);
                    return;
                }
            }
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        }
    };

    private View.OnClickListener lightButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (cameraView.getCameraControl().getFlashMode() == ICameraControl.FLASH_MODE_OFF) {
                cameraView.getCameraControl().setFlashMode(ICameraControl.FLASH_MODE_TORCH);
            } else {
                cameraView.getCameraControl().setFlashMode(ICameraControl.FLASH_MODE_OFF);
            }
            updateFlashMode();
        }
    };

    private View.OnClickListener takeButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cameraView.takePicture(outputFile, takePictureCallback);
        }
    };

    private CameraView.OnTakePictureCallback autoTakePictureCallback = new CameraView.OnTakePictureCallback() {
        @Override
        public void onPictureTaken(final Bitmap bitmap) {
            CameraThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                        bitmap.recycle();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /*Intent intent = new Intent();
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, contentType);
                    getActivity().setResult(Activity.RESULT_OK, intent);*/
                }
            });
        }
    };

    private CameraView.OnTakePictureCallback takePictureCallback = new CameraView.OnTakePictureCallback() {
        @Override
        public void onPictureTaken(final Bitmap bitmap) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    takePictureContainer.setVisibility(View.INVISIBLE);
                    if (cropMaskView.getMaskType() == MaskView.MASK_TYPE_NONE) {
                        cropView.setFilePath(outputFile.getAbsolutePath());
                        showCrop();
                    } else if (cropMaskView.getMaskType() == MaskView.MASK_TYPE_BANK_CARD) {
                        cropView.setFilePath(outputFile.getAbsolutePath());
                        cropMaskView.setVisibility(View.INVISIBLE);
                        overlayView.setVisibility(View.VISIBLE);
                        overlayView.setTypeWide();
                        showCrop();
                    } else {
                        displayImageView.setImageBitmap(bitmap);
                        showResultConfirm();
                    }
                }
            });
        }
    };

    private View.OnClickListener cropCancelButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 释放 cropView中的bitmap;
            cropView.setFilePath(null);
            showTakePicture();
        }
    };

    private View.OnClickListener cropConfirmButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int maskType = cropMaskView.getMaskType();
            Rect rect;
            switch (maskType) {
                case MaskView.MASK_TYPE_BANK_CARD:
                case MaskView.MASK_TYPE_ID_CARD_BACK:
                case MaskView.MASK_TYPE_ID_CARD_FRONT:
                    rect = cropMaskView.getFrameRect();
                    break;
                case MaskView.MASK_TYPE_NONE:
                default:
                    rect = overlayView.getFrameRect();
                    break;
            }
            Bitmap cropped = cropView.crop(rect);
            displayImageView.setImageBitmap(cropped);
            cropAndConfirm();
        }
    };

    private void cropAndConfirm() {
        cameraView.getCameraControl().pause();
        updateFlashMode();
        doConfirmResult();
    }

    //take then crop,finally do confirm
    private void doConfirmResult() {
        CameraThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                    Bitmap bitmap = ((BitmapDrawable) displayImageView.getDrawable()).getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.close();
//                    Logger.d(outputFile.length() + '\n' + outputFile.getPath());
                    RecognizeService.recGeneral(outputFile.getPath(), serviceListener);

                    //reset camare
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.VISIBLE);
                            displayImageView.setImageBitmap(null);
                            showTakePicture();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * picture ocr recognize result listener
     */
    private RecognizeService.ServiceListener serviceListener = new RecognizeService.ServiceListener() {
        @Override
        public void onResult(String jsonResult) {
            StringBuilder strResult = new StringBuilder();
            List<OcrGenWords.WordsResultBean> wordsResults = new Gson().fromJson(jsonResult, OcrGenWords.class).getWords_result();
            for (OcrGenWords.WordsResultBean wordsResultBean : wordsResults) {
                strResult.append(wordsResultBean.getWords());
            }

            Logger.d(strResult.toString());
            showNotebookDialog(strResult.toString());
        }
    };

    private View.OnClickListener confirmButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doConfirmResult();
        }
    };

    private View.OnClickListener confirmCancelButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            displayImageView.setImageBitmap(null);
            showTakePicture();
        }
    };

    private View.OnClickListener rotateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cropView.rotate(90);
        }
    };

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = null;
        try {
            cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setOrientation(newConfig);
    }

    private void setOrientation(Configuration newConfig) {
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        int orientation;
        int cameraViewOrientation = CameraView.ORIENTATION_PORTRAIT;
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                cameraViewOrientation = CameraView.ORIENTATION_PORTRAIT;
                orientation = OCRCameraLayout.ORIENTATION_PORTRAIT;
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                orientation = OCRCameraLayout.ORIENTATION_HORIZONTAL;
                if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90) {
                    cameraViewOrientation = CameraView.ORIENTATION_HORIZONTAL;
                } else {
                    cameraViewOrientation = CameraView.ORIENTATION_INVERT;
                }
                break;
            default:
                orientation = OCRCameraLayout.ORIENTATION_PORTRAIT;
                cameraView.setOrientation(CameraView.ORIENTATION_PORTRAIT);
                break;
        }
        takePictureContainer.setOrientation(orientation);
        cameraView.setOrientation(cameraViewOrientation);
        cropContainer.setOrientation(orientation);
        confirmResultContainer.setOrientation(orientation);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                cropView.setFilePath(getRealPathFromURI(uri));
                showCrop();
            } else {
                cameraView.getCameraControl().resume();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraView.getCameraControl().refreshPermission();
                } else {
                    Toast.makeText(getApplicationContext(), com.baidu.ocr.ui.R.string.camera_permission_required, Toast.LENGTH_LONG)
                            .show();
                }
                break;
            }
            case PERMISSIONS_EXTERNAL_STORAGE:
            default:
                break;
        }
    }

    private void showNotebookDialog(String notebookStr) {
        mProgressBar.setVisibility(View.GONE);

        final EditText notebookText = new EditText(getActivity());
        notebookText.setText(notebookStr);
        notebookText.setTextSize(18);
        notebookText.setTextColor(Color.GRAY);
        notebookText.setGravity(Gravity.CENTER);

        AlertDialog notebookDialog = new AlertDialog.Builder(getActivity())
                .setTitle("笔记")
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String edittedNotebook = notebookText.getText().toString();
                        String username = "123456";
                        if (BmobUser.getCurrentUser(getActivity()) != null) {
                            username = BmobUser.getCurrentUser(getActivity()).getUsername();
                        }
                        new Note(edittedNotebook, username).save(getActivity(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Logger.d("note book upload succeed!");
                                Toast.makeText(getActivity(), "保存成功!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });

                    }
                })
                .create();


        notebookDialog.setView(notebookText,20,10,20,10);
        notebookDialog.show();
    }


    /**
     * 做一些收尾工作
     */
    private void doClear() {
        CameraThreadPool.cancelAutoFocusTimer();
        if (isNativeEnable && !isNativeManual) {
            IDcardQualityProcess.getInstance().releaseModel();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.doClear();
    }


}