//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.xxt.actionsheet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import java.util.Arrays;
import java.util.List;

public class ActionSheet extends Dialog implements OnClickListener {
    private static final int CANCEL_BUTTON_ID = 100;
    private static final int TRANSLATE_DURATION = 300;
    private static final int ALPHA_DURATION = 300;
    private Context mContext;
    private ActionSheet.MenuItemClickListener menuItemClickListener;
    private ActionSheet.MenuCancelButtonClickListener menuCancelButtonClickListener;
    private View mView;
    private RelativeLayout rlPanel;
    private LinearLayout llItems;
    private LinearLayout llItemsWithTitle;
    private Button cancelBtn;
    private Button titleBtn;
    private LinearLayout llTitle;
    private View viewBg;
    private List<String> items;
    private String cancelTitle = "取消";
    private String title = "";
    private boolean mCancelableOnTouchOutside = true;
    private boolean mDismissed = true;
    private double alphaNum = 0.7;

    public ActionSheet(Context context) {
        super(context, android.R.style.Theme_Light_NoTitleBar);
        this.mContext = context;
        this.initViews();
        this.getWindow().setGravity(80);
        Drawable drawable = new ColorDrawable();
        drawable.setAlpha(0);
        this.getWindow().setBackgroundDrawable(drawable);
    }

    public ActionSheet setCancelableOnTouchMenuOutside(boolean cancelable) {
        this.mCancelableOnTouchOutside = cancelable;
        return this;
    }

    public ActionSheet setTitle(String titleStr) {
        if (titleStr != null) {
            this.title = titleStr;
        }

        return this;
    }

    public ActionSheet setCancelButtonTitle(String cancelTitleStr) {
        if (cancelTitleStr != null) {
            this.cancelTitle = cancelTitleStr;
        }

        return this;
    }

    public ActionSheet setCancelButtonGone() {
        this.cancelBtn.setVisibility(View.GONE);
        LayoutParams lp = (LayoutParams)this.llItemsWithTitle.getLayoutParams();
        lp.addRule(12);
        this.llItemsWithTitle.setLayoutParams(lp);
        return this;
    }

    public ActionSheet addItems(String... titles) {
        if (titles != null && titles.length != 0) {
            this.items = Arrays.asList(titles);
            return this;
        } else {
            return this;
        }
    }

    public ActionSheet addItemList(List<String> titleList) {
        this.items = titleList;
        return this;
    }

    public ActionSheet setItemClickListener(ActionSheet.MenuItemClickListener listener) {
        this.menuItemClickListener = listener;
        return this;
    }

    public ActionSheet setCancelButtonClickListener(ActionSheet.MenuCancelButtonClickListener listener) {
        this.menuCancelButtonClickListener = listener;
        return this;
    }

    public ActionSheet setAlphaNum(double alphaNum) {
        this.alphaNum = alphaNum;
        return this;
    }

    public void showMenu() {
        this.createItems();
        if (this.mDismissed) {
            this.viewBg.setBackgroundColor(Color.argb((int)(alphaNum*255), 0, 0, 0));
            this.show();
            this.getWindow().setContentView(this.mView);
            this.mDismissed = false;
        }
    }

    private void initViews() {
        InputMethodManager imm = (InputMethodManager)this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            View focusView = ((Activity)this.mContext).getCurrentFocus();
            if (focusView != null) {
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }
        }

        LayoutInflater inflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mView = inflater.inflate(R.layout.action_sheet, (ViewGroup)null);
        this.viewBg = this.mView.findViewById(R.id.view_bg);
        this.viewBg.setBackgroundColor(Color.argb((int)(alphaNum*255), 0, 0, 0));
        this.viewBg.setOnClickListener(this);
        this.rlPanel = (RelativeLayout)this.mView.findViewById(R.id.rl_panel);
        this.llItemsWithTitle = (LinearLayout)this.mView.findViewById(R.id.ll_items_with_title);
        this.llTitle = (LinearLayout)this.mView.findViewById(R.id.ll_title);
        this.titleBtn = (Button)this.mView.findViewById(R.id.btn_title);
        this.llItems = (LinearLayout)this.mView.findViewById(R.id.ll_items);
        this.cancelBtn = (Button)this.mView.findViewById(R.id.btn_cancle_title);
        this.cancelBtn.setOnClickListener(this);
        this.viewBg.startAnimation(this.createAlphaInAnimation());
        this.rlPanel.startAnimation(this.createTranslationInAnimation());
    }

    private Animation createTranslationInAnimation() {
        int type = 1;
        TranslateAnimation an = new TranslateAnimation(type, 0.0F, type, 0.0F, type, 1.0F, type, 0.0F);
        an.setDuration(300L);
        return an;
    }

    private Animation createAlphaInAnimation() {
        AlphaAnimation an = new AlphaAnimation(0.0F, 1.0F);
        an.setDuration(300L);
        return an;
    }

    private Animation createTranslationOutAnimation() {
        int type = 1;
        TranslateAnimation an = new TranslateAnimation(type, 0.0F, type, 0.0F, type, 0.0F, type, 1.0F);
        an.setDuration(300L);
        an.setFillAfter(true);
        return an;
    }

    private Animation createAlphaOutAnimation() {
        AlphaAnimation an = new AlphaAnimation(1.0F, 0.0F);
        an.setDuration(300L);
        an.setFillAfter(true);
        return an;
    }

    private void createItems() {
        if ("".equals(this.title)) {
            this.llTitle.setVisibility(View.GONE);
        } else {
            this.llTitle.setVisibility(View.VISIBLE);
            this.titleBtn.setText(this.title);
        }

        this.cancelBtn.setText(this.cancelTitle);
        if (this.items != null && this.items.size() > 0) {
            for(int i = 0; i < this.items.size(); ++i) {
                LayoutInflater inflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewItem = inflater.inflate(R.layout.item_action_sheet, (ViewGroup)null);
                viewItem.setId(CANCEL_BUTTON_ID + i + 1);
                viewItem.setOnClickListener(this);
                TextView tvItem = (TextView)viewItem.findViewById(R.id.tv_item);
                View viewDivider = viewItem.findViewById(R.id.view_divider);
                tvItem.setText((CharSequence)this.items.get(i));
                if (i == this.items.size() - 1) {
                    viewDivider.setVisibility(View.GONE);
                }

                this.llItems.addView(viewItem);
            }
        }

    }

    public void dismissMenu() {
        if (!this.mDismissed) {
            this.dismiss();
            this.onDismiss();
            this.mDismissed = true;
        }
    }

    private void onDismiss() {
        this.rlPanel.startAnimation(this.createTranslationOutAnimation());
        this.viewBg.startAnimation(this.createAlphaOutAnimation());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.cancelBtn.getId()) {
            this.dismissMenu();
            if (this.menuCancelButtonClickListener != null) {
                this.menuCancelButtonClickListener.onCancelButtonClick();
            }
        } else if (v.getId() == this.viewBg.getId()) {
            if (!this.mCancelableOnTouchOutside) {
                return;
            }

            this.dismissMenu();
            if (this.menuCancelButtonClickListener != null) {
                this.menuCancelButtonClickListener.onCancelButtonClick();
            }
        } else {
            if (v.getId() == this.cancelBtn.getId() || v.getId() == this.viewBg.getId()) {
                return;
            }

            this.dismissMenu();
            if (this.menuItemClickListener != null) {
                this.menuItemClickListener.onItemClick(v.getId() - CANCEL_BUTTON_ID - 1);
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.dismissMenu();
            if (this.menuCancelButtonClickListener != null) {
                this.menuCancelButtonClickListener.onCancelButtonClick();
            }

            return true;
        } else {
            return false;
        }
    }

    public interface MenuCancelButtonClickListener {
        void onCancelButtonClick();
    }

    public interface MenuItemClickListener {
        void onItemClick(int var1);
    }
}
