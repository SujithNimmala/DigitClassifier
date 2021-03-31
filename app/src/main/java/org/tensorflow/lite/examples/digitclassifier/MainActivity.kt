package org.tensorflow.lite.examples.digitclassifier

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.Button
import android.widget.TextView
import com.divyanshu.draw.widget.DrawView

class MainActivity : AppCompatActivity() {

  private lateinit var selectImageButton : Button
  private lateinit var makePrediction : Button
  private lateinit var imgView : ImageView
  private lateinit var textView : TextView
//  private var drawView: DrawView? = null
//  private var clearButton: Button? = null
//  private var predictedTextView: TextView? = null
  private var digitClassifier = DigitClassifier(this)

//  @SuppressLint("ClickableViewAccessibility")
//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.tfe_dc_activity_main)
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.tfe_dc_activity_main)

      selectImageButton = findViewById(R.id.button)
      makePrediction = findViewById(R.id.button2)
      imgView = findViewById(R.id.imageView2)
      textView = findViewById(R.id.textView)

      val labels = application.assets.open("labels.txt").bufferedReader().use { it.readText() }.split(
        "\n"
      )

    // Setup view instances
//    ImageView = findViewById(R.id.draw_view)
//    drawView?.setStrokeWidth(70.0f)
//    drawView?.setColor(Color.WHITE)
//    drawView?.setBackgroundColor(Color.BLACK)
//    clearButton = findViewById(R.id.clear_button)
//    predictedTextView = findViewById(R.id.predicted_text)

    // Setup clear drawing button
//    clearButton?.setOnClickListener {
//      drawView?.clearCanvas()
//      predictedTextView?.text = getString(R.string.tfe_dc_prediction_text_placeholder)
//    }

    // Setup classification trigger so that it classify after every stroke drew
//    drawView?.setOnTouchListener { _, event ->
//      // As we have interrupted DrawView's touch event,
//      // we first need to pass touch events through to the instance for the drawing to show up
//      drawView?.onTouchEvent(event)
//
//      // Then if user finished a touch event, run classification
//      if (event.action == MotionEvent.ACTION_UP) {
//        classifyDrawing()
//      }
//
//      true
//    }
  selectImageButton.setOnClickListener {
//            Log.d("mssg", "button pressed") // Required only when used in non-android situations
    val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)

    intent.type = "image/*"

    startActivityForResult(intent, 100)
  }
    // Setup digit classifier
    digitClassifier
      .initialize()
      .addOnFailureListener { e -> Log.e(TAG, "Error to setting up digit classifier.", e) }
  }

  override fun onDestroy() {
    digitClassifier.close()
    super.onDestroy()
  }

  private fun classifyDrawing() {
    val bitmap = ImageView?.getBitmap()

    if ((bitmap != null) && (digitClassifier.isInitialized)) {
      digitClassifier
        .classifyAsync(bitmap)
        .addOnSuccessListener { resultText -> TextView?.text = resultText }
        .addOnFailureListener { e ->
          TextView?.text = getString(
            R.string.tfe_dc_classification_error_message,
            e.localizedMessage
          )
          Log.e(TAG, "Error classifying drawing.", e)
        }
    }
  }

  companion object {
    private const val TAG = "MainActivity"
  }
}
