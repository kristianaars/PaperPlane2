package paperplane.android.me.aars.paperplane.Managers;

import android.util.Log;

import paperplane.android.me.aars.paperplane.GameView;
import paperplane.android.me.aars.paperplane.R;

/**
 * Created by Kristian Aars on 13.07.2016.
 */

public class MoneyManager {

    private final String MONEY_SAVEID;

    private GameSaveFile fileManager;

    public MoneyManager(GameSaveFile fileMngr, GameView g) {
        MONEY_SAVEID = g.getResources().getString(R.string.saveid_money);
        fileManager = fileMngr;
    }

    public int getValue() {
        String rawData = fileManager.getData(MONEY_SAVEID);
        int value;

        try {
            value = Integer.parseInt(rawData);
        } catch (Exception e) {
            Log.e("MoneyManager", "Error recieving moneyamount from file, setting value to 0");
            value = 0;
            setValue(value);
        }

        return value;
    }

    public boolean widthdraw(int amount) {
        if(!testWithdraw(amount)) return false;

        int value = getValue();
        int newValue = value - amount;

        if(setValue(newValue)) return true;
        else return false;
    }

    public boolean deposit(int amount) {
        int value = getValue();
        int newValue = value + amount;

        if(setValue(newValue)) return true;
        else return false;
    }

    public boolean testWithdraw(int amount) {
        int value = getValue();
        if((value - amount) < 0) return false;
        else return true;
    }

    private boolean setValue(int value) {
        if(value < 0) return false;

        String formattedValue = "" + value;
        fileManager.setData(MONEY_SAVEID, formattedValue);

        if(fileManager.getData(MONEY_SAVEID).equals(formattedValue)) {
            return true;
        } else return false;
    }
}
