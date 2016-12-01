package dateadog.dateadog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimePickerFragment} factory method to
 * create an instance of this fragment.
 */
public class TimePickerFragment extends DialogFragment  {

    private TimePicker timePicker;

    public interface TimeDialogListener {
        void onFinishDialog(int hour, int minute);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_time_picker, null);

        timePicker = (TimePicker) v.findViewById(R.id.dialog_time_picker);
        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_time_question)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TimeDialogListener activity = (TimeDialogListener) getActivity();
                                activity.onFinishDialog(timePicker.getHour(), timePicker.getMinute());
                                dismiss();
                            }
                        })
                .create();
    }
}
