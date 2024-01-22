package fastcampus.part1.converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import fastcampus.part1.converter.databinding.ActivityMainBinding

/**
 * 단위 변환기 앱
 *
 * 1. cm ↔️ m 변환
 * 2. 값 입력 시, 변환된 값 노출
 * 3. 입출력 단위 변경 가능
 * 4. 연산 체크
 *    cm → m : *0.01
 *    m → cm : *100
 * */

/**
 * ConstraintLayout
 * flat 구조로 화면 렌더링 속도 증가
 *
 * ViewBinding
 * findViewById 보다 안전한 UI 선택
 *
 * onSaveInstanceState
 * 원하는 데이터 저장
 *
 * onRestoreInstanceState
 * 저장한 데이터 부르기
 * */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var inputNumber: Int = 0
    private var isUnitMeter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val inputEditText = binding.inputEditText
        val inputUnitTextView = binding.inputUnitTextView
        val outputTextView = binding.outputTextView
        val outputUnitTextView = binding.outputUnitTextView
        val unitConvertButton = binding.unitConvertButton


        inputEditText.addTextChangedListener { text ->
            inputNumber = if (text.isNullOrEmpty()) 0 else text.toString().toInt()

            if (isUnitMeter) {
                outputTextView.text = (inputNumber * 100).toString()
            } else {
                outputTextView.text = (inputNumber * 0.01).toString()
            }
        }

        unitConvertButton.setOnClickListener {
            isUnitMeter = isUnitMeter.not()
            // isUnitMeter = !isUnitMeter

            if (isUnitMeter) {
                inputUnitTextView.text = "m"
                outputUnitTextView.text = "cm"
                outputTextView.text = inputNumber.times(100).toString()
            } else {
                inputUnitTextView.text = "cm"
                outputUnitTextView.text = "m"
                outputTextView.text = inputNumber.times(0.01).toString()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isUnitMeter", isUnitMeter)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Activity의 View 상태 복원
        // 첫번째 줄 : 단위 변환에 따른 값 변경이 바로 반영 X
        // super.onRestoreInstanceState(savedInstanceState)

        isUnitMeter = savedInstanceState.getBoolean("isUnitMeter")
        binding.inputUnitTextView.text = if (isUnitMeter) "m" else "cm"
        binding.outputUnitTextView.text = if (isUnitMeter) "cm" else "m"

        // 마지막 줄 : 단위 변환에 따른 값 변경이 바로 반영 O
        super.onRestoreInstanceState(savedInstanceState)
    }
}