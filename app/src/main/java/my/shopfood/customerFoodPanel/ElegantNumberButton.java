package my.shopfood.customerFoodPanel;

import android.content.DialogInterface;
import android.view.View;

public class ElegantNumberButton {
    public void setNumber(String dishQuantity) {

    }

    public void setOnValueChangeListener(ElegantNumberButton.OnValueChangeListener onValueChangeListener) {

    }

    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        
    }

    public String getNumber() {
        return null;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
    }

    public interface OnClickListener {
        void onClick(View view);
    }

    public abstract static class OnValueChangeListener {
        public abstract void onValueChange(ElegantNumberButton view, int oldValue, int newValue);
    }
}
