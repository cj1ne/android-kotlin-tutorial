package com.example.its_2.flashlight

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class TorchAppWidget : AppWidgetProvider() {

    // 위젯이 업데이트 되어야 할 때 호출
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    // 위젯이 처음 생성될 때 호출
    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    // 마지막 위젯이 제거될 때 호출
    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        // 위젯을 업데이트할 때 수행되는 코드
        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

            val widgetText = context.getString(R.string.appwidget_text)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.torch_app_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            // 실행할 Intent 작성
            val intent = Intent(context, TorchService::class.java)
            val pendingIntent = PendingIntent.getService(context, 0, intent, 0)

            // 위젯을 클릭하면 위에서 정의한 Intent 실행
            views.setOnClickPendingIntent(R.id.appwidget_layout, pendingIntent)

            // 위젯 관리자에게 위젯을 업데이트하도록 지시
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

