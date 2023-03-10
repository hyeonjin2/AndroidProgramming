import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.neck_is_turtle.R;

import java.util.Calendar;

public class mDialogFragment extends DialogFragment implements View.OnClickListener{

    private int hour, minute;
    TimePicker timePicker;
    Calendar calendar;

    public mDialogFragment(){
        // 시간 Calendar에서 현재시간 가져오기
        calendar = Calendar.getInstance();
        hour = calendar.get(calendar.HOUR_OF_DAY);
        minute = calendar.get(calendar.MINUTE);
    }
    public void setDialog(int h, int m){
        // 들어온 값 시간으로 설정하기
        hour = h;
        minute = m;
    }
    public static mDialogFragment getInstance(){
        mDialogFragment mDialog = new mDialogFragment();
        return mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_alert, container);
        Button btn_set = view.findViewById(R.id.set);
        Button btn_close = view.findViewById(R.id.btn_close);
        btn_set.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        // 타임피커 설정된 알람 값으로 보여주기
        timePicker = view.findViewById(R.id.timePicker);
        timePicker.setHour(hour);
        timePicker.setMinute(minute);
        // 선택된 요일 들어온 값으로 보여주기

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
