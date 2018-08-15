package horizont.com.pmart.horizon.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;

public class ClDialogBox extends DialogFragment {
    public static ProgressDialog getHttpLoading(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("");
        progressDialog.setMessage("กำลังโหลด...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static AlertDialog.Builder gerErrorDialog(Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("เกิดข้อผิดพลาด");
        alertDialog.setCancelable(true);
        return alertDialog;
    }
}
