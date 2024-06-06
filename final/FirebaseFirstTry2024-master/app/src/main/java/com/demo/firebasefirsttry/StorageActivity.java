package com.demo.firebasefirsttry;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class StorageActivity extends AppCompatActivity {
    private static final String TAG = "StorageActivity";
    private static final int PICK_FILE_REQUEST = 1;
    private StorageReference mStorageRef;
    private EditText mFileNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        // 初始化Firebase
        FirebaseApp.initializeApp(this);

        // 获取Storage引用
        mStorageRef = FirebaseStorage.getInstance().getReference();

        // 上传按钮
        Button uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(v -> openFileChooser());

        // 下载按钮
        Button downloadButton = findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(v -> downloadFile());

        // 文件名输入框
        mFileNameEditText = findViewById(R.id.fileNameEditText);
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();
            uploadFile(fileUri);
        }
    }

    private void uploadFile(Uri fileUri) {
        if (fileUri != null) {
            try {
                ContentResolver contentResolver = getContentResolver();
                InputStream inputStream = contentResolver.openInputStream(fileUri);

                if (inputStream != null) {
                    // 获取文件名
                    String fileName = getFileName(fileUri);

                    StorageReference fileRef = mStorageRef.child("uploads/" + fileName);

                    // 上传文件
                    UploadTask uploadTask = fileRef.putStream(inputStream);
                    uploadTask.addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(StorageActivity.this, "Upload successful! File: " + fileName, Toast.LENGTH_LONG).show();
                    }).addOnFailureListener(e -> {
                        Log.e(TAG, "Upload failed", e);
                        Toast.makeText(StorageActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            } catch (IOException e) {
                Log.e(TAG, "File upload failed", e);
                Toast.makeText(this, "File upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e(TAG, "File URI is null");
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadFile() {
        String fileName = mFileNameEditText.getText().toString().trim();
        if (fileName.isEmpty()) {
            Toast.makeText(this, "Please enter a file name", Toast.LENGTH_SHORT).show();
            return;
        }

        // 指定存储路径
        StorageReference fileRef = mStorageRef.child("uploads/" + fileName);

        // 创建本地文件
        File localFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);

        // 下载文件
        fileRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // 文件下载成功
                Toast.makeText(StorageActivity.this, "File Downloaded", Toast.LENGTH_SHORT).show();
                openFile(localFile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // 文件下载失败
                Log.e(TAG, "File download failed", exception);
                Toast.makeText(StorageActivity.this, "File download failed: " + exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openFile(File file) {
        // 获取文件的URI
        Uri fileUri = Uri.fromFile(file);

        // 创建Intent
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // 检查是否有应用可以处理该Intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No application to open the file", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
