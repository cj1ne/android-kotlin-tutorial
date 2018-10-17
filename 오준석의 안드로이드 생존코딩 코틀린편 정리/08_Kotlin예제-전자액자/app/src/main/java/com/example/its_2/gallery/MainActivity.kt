package com.example.its_2.gallery

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 권한이 부여되었는지 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 사용자가 전에 권한 요청을 거부했는지 확인
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.e("Permission", "이전에 거부")
                //  이전에 이미 권한이 거부된 경우
                alert("사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다", "권한이 필요한 이유") {
                    yesButton {
                        // 권한 요청
                        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                                , REQUEST_READ_EXTERNAL_STORAGE)
                    }
                    noButton {

                    }
                }.show()
            } else {
                Log.e("Permission", "처음")
                // 권한 요청
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        , REQUEST_READ_EXTERNAL_STORAGE)
            }
        } else {
            // 권한이 이미 허용된 경우
            getAllPhotos()

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty()) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // 권한 허용
                    getAllPhotos()
                } else {
                    // 권 거부
                    toast("권한이 거부되었습니다.")
                }
                return
            }
        }
    }

    private fun getAllPhotos() {
        // 모든 사진 정보 가져오기
        var cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // 어떤 데이터를 가져오는지 지정
                null, // 어떤 항목의 데이터를 가져올 지 지정, 잘 모르는 경우 null 설정
                null, // 데이터를 가져올 조건을 지정, 전체 데이터를 가져올 때는 null 설정
                null, // selection 인자와 조합하여 조건 지정, 사용하지 않는 경우 null 설정
                MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC") // 정렬 방법 지정

        var fragments = ArrayList<Fragment>()

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 사진 경로 uri 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("MainActivity", uri)
                fragments.add(PhotoFragment.newInstance(uri))
            }
            cursor.close()
        }

        // 어댑터
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.updateFragments(fragments)
        viewPager.adapter = adapter

        // 3초마다 자동 슬라이드
        timer(period = 3000) {
            runOnUiThread {
                if (viewPager.currentItem < adapter.count - 1) {
                    viewPager.currentItem = viewPager.currentItem + 1
                } else {
                    viewPager.currentItem = 0
                }
            }
        }
    }
}
