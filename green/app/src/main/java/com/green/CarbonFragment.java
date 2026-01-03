package com.green;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.green.R;

public class CarbonFragment extends Fragment {

    private EditText etElectricity, etTransport, etFood;
    private TextView tvResult;
    private Button btnCalculate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carbon, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        etElectricity = view.findViewById(R.id.et_electricity);
        etTransport = view.findViewById(R.id.et_transport);
        etFood = view.findViewById(R.id.et_food);
        tvResult = view.findViewById(R.id.tv_result);
        btnCalculate = view.findViewById(R.id.btn_calculate);

        btnCalculate.setOnClickListener(v -> calculateCarbonFootprint());
    }

    private void calculateCarbonFootprint() {
        // 获取输入值
        float electricity = getInputValue(etElectricity);
        float transport = getInputValue(etTransport);
        float food = getInputValue(etFood);

        // 验证输入
        if (electricity == 0 && transport == 0 && food == 0) {
            Toast.makeText(getActivity(), "请至少输入一项数据", Toast.LENGTH_SHORT).show();
            return;
        }

        // 计算碳排放量 (单位: kg/天)
        double electricityCarbon = electricity * 0.5;  // 每度电约0.5kg CO2
        double transportCarbon = transport * 0.27;   // 每公里约0.27kg CO2
        double foodCarbon = food * 0.8;              // 每人每天饮食约0.8kg CO2

        double totalCarbon = electricityCarbon + transportCarbon + foodCarbon;

        // 更新结果显示
        tvResult.setText(String.format("你的碳排放量: %.2f kg/天", totalCarbon));
    }

    private float getInputValue(EditText editText) {
        String text = editText.getText().toString();
        return TextUtils.isEmpty(text) ? 0 : Float.parseFloat(text);
    }
}