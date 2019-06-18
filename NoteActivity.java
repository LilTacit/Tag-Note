package com.mobile.kiril.tagnote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.kiril.tagnote.toastEnum.ToastErrors;
import com.mobile.kiril.tagnote.toastEnum.ToastWarnings;
import com.mobile.kiril.tagnote.entities.Category;
import com.mobile.kiril.tagnote.entities.Note;
import com.mobile.kiril.tagnote.entities.Tag;
import com.mobile.kiril.tagnote.fordatabase.DBHelper;
import com.mobile.kiril.tagnote.presenters.NotePresenter;
import com.mobile.kiril.tagnote.spotRowBuilder.SpotRowBuilderMain;
import com.mobile.kiril.tagnote.textWatchers.NoteSaveTextWatcher;
import com.mobile.kiril.tagnote.textWatchers.SearchCategoryTextWatcher;
import com.mobile.kiril.tagnote.textWatchers.SearchTagTextWatcher;
import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeDefaultMock;
import com.mobile.kiril.tagnote.themes.ThemeSetter;
import com.mobile.kiril.tagnote.themes.ThemeViewWrap;
import com.mobile.kiril.tagnote.themes.ThemeWrapType;
import com.mobile.kiril.tagnote.touchListeners.NoteActivityTouchListener;
import com.mobile.kiril.tagnote.views.NoteView;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import org.apmem.tools.layouts.FlowLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener, NoteView {
    private ImageButton backToMainBtn;
    private EditText noteTextEt, changeCategoryEt, changeTagEt;
    private NotePresenter notePresenter;
    private DBHelper dbHelper;
    private Intent intent;
    private boolean isExist;
    private String openCategory, noteCategory, text, date, spot, testColor;
    private int id, cacheModZoneNumber, changeCloseYSize, changeMenuYSize, screenXSize, screenYSize, cacheChangeMod, tempCacheChangeMod;
    private float changeMenuYSizeFloat, changeCloseYSizeFloat, screenXSizeFloat, screenYSizeFloat, changeMenuYSizeMain;
    private boolean isChangeModOn, isChangeContainerShow, isPopupOpen, isOpenAnim, isPopupAnim;
    private LinearLayout changeModContainer, changeModCategory, changeModTag, changeModSpot, changeModCloseZone, changeModCategoryList,
            changeModClearCategory, changeModSpotCurrentContainer, popupNotificationContainer, changeModTagExistList, changeModMenu,
            changeModTagEditBox, changeModCategoryEditBox, swipeZone, changeModSpotIcContainer, barContainer, changeModBarBorder2, changeModBarBorder3, changeModBarBorder1,
            changeModClearTag, popupRemoveBlurView, popupRemoveBox, popupRemoveBorderTop, topline;
    private RelativeLayout barTagBtn, barCategoryBtn, barSpotBtn, barRemoveBtn, removeBtn, changeCategoryAddBtn, changeTagAddBtn,
            changeModScreen, changeModSpotViewContainer, changeModCurrentSpotViewContainer, clearTagAcBox, clearCategoryAcBox;
    private Button changeBtn, popupRemoveBtnYes, popupRemoveBtnNo;
    private TextView changeModCurrentCategory, clearTagTextView, clearCategoryTextView, popupRemoveTitle;
    private FlowLayout changeModTagList;
    private ImageView changeTagAddIc, clearTagIc, changeCategoryAddIc, clearCategoryIc, popupRemoveIc, icRemove, changeModTagHideZone1, changeModTagHideZone2,
            changeModCategoryHideZone1, changeModCategoryHideZone2;
    private List<Tag> tags;
    private SpotManager spotManager;
    private List<String> spotIcList;
    private List<View> changeModContainersList, changeModBtnsList;
    private SpotRowBuilderMain spotRowBuilderMain;
    private List<ThemeViewWrap> themeWrapsList;
    private SharedPreferences themePref;
    private Map<String, Integer> mapColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //hideZones
        changeModTagHideZone1 = findViewById(R.id.changeModTagHideZone1);
        changeModTagHideZone2 = findViewById(R.id.changeModTagHideZone2);
        changeModCategoryHideZone1 = findViewById(R.id.changeModCategoryHideZone1);
        changeModCategoryHideZone2 = findViewById(R.id.changeModCategoryHideZone2);

        //tech + main
        dbHelper = new DBHelper(this);
        notePresenter = new NotePresenter(this, dbHelper);
        noteTextEt = (EditText) findViewById(R.id.noteTextEt);
        intent = getIntent();
        isExist = intent.getBooleanExtra("isExist", false);
        openCategory = intent.getStringExtra("category");
        isChangeModOn = false;
        isChangeContainerShow = false;
        backToMainBtn = (ImageButton) findViewById(R.id.backToMainBtn);
        topline = (LinearLayout) findViewById(R.id.topline);
        icRemove = (ImageView) findViewById(R.id.icRemove);
        backToMainBtn.setOnClickListener(this);
        tags = new ArrayList<>();
        spotManager = new SpotManager();
        spotIcList = spotManager.getColorIcList();
        removeBtn = (RelativeLayout) findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener(this);
        changeModContainersList = new ArrayList<>();
        changeModBtnsList = new ArrayList<>();
        isOpenAnim = false;
        isPopupAnim = false;
        cacheChangeMod = -1;
        tempCacheChangeMod = -1;
        themeWrapsList = new ArrayList<>();
        mapColor = new HashMap<>();
        testColor = "FF00F2";

        //displaySize
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenXSize = size.x;
        screenYSize = size.y;
        screenXSizeFloat = size.x;
        screenYSizeFloat = size.y;
        changeMenuYSizeFloat = screenYSizeFloat/100*75;
        changeCloseYSizeFloat = screenYSizeFloat/100*25;
        spotRowBuilderMain = new SpotRowBuilderMain(screenYSizeFloat, screenXSizeFloat, getResources().getDisplayMetrics().density);

        if (isExist) {
            id = intent.getIntExtra("noteId", 0);
            text = intent.getStringExtra("noteText");
            spot = intent.getStringExtra("noteSpot");
            date = intent.getStringExtra("noteDate");
            noteCategory = intent.getStringExtra("noteCategory");

            //tags
            String[] existTags = intent.getStringArrayExtra("noteTagNames");
            for (String s : existTags){
                if(!s.equalsIgnoreCase("")) {
                    Tag tag = new Tag();
                    tag.setName(s);
                    tags.add(tag);
                }
            }

            noteTextEt.setText(text);
        } else {
            spot = "";

            if(!openCategory.equalsIgnoreCase("")) noteCategory = openCategory;
            else noteCategory = "";
        }

        //changeModForTheme
        changeTagAddIc = findViewById(R.id.changeTagAddIc);
        clearTagAcBox = findViewById(R.id.clearTagAcBox);
        clearTagIc = findViewById(R.id.clearTagIc);
        changeModClearTag = findViewById(R.id.changeModClearTag);
        clearTagTextView = findViewById(R.id.clearTagTextView);
        changeCategoryAddIc = findViewById(R.id.changeCategoryAddIc);
        clearCategoryAcBox = findViewById(R.id.clearCategoryAcBox);
        clearCategoryIc = findViewById(R.id.clearCategoryIc);
        clearCategoryTextView = findViewById(R.id.clearCategoryTextView);
        popupRemoveBlurView = findViewById(R.id.popupRemoveBlurView);
        popupRemoveBox = findViewById(R.id.popupRemoveBox);
        popupRemoveIc = findViewById(R.id.popupRemoveIc);
        popupRemoveTitle = findViewById(R.id.popupRemoveTitle);
        popupRemoveBorderTop = findViewById(R.id.popupRemoveBorderTop);
        //changeModBarBtn
        changeModBarBorder1 = (LinearLayout) findViewById(R.id.changeModBarBorder1);
        changeModBarBorder2 = (LinearLayout) findViewById(R.id.changeModBarBorder2);
        changeModBarBorder3 = (LinearLayout) findViewById(R.id.changeModBarBorder3);
        barContainer = (LinearLayout) findViewById(R.id.barContainer);
        barTagBtn = (RelativeLayout) findViewById(R.id.barTagBtn);
        barTagBtn.setOnClickListener(this);
        barCategoryBtn = (RelativeLayout) findViewById(R.id.barCategoryBtn);
        barCategoryBtn.setOnClickListener(this);
        barSpotBtn = (RelativeLayout) findViewById(R.id.barSpotBtn);
        barSpotBtn.setOnClickListener(this);
        barRemoveBtn = (RelativeLayout) findViewById(R.id.barRemoveBtn);
        barRemoveBtn.setOnClickListener(this);

        changeModMenu = (LinearLayout) findViewById(R.id.changeModMenu);
        changeModScreen = (RelativeLayout) findViewById(R.id.changeModScreen);
        changeModContainer = (LinearLayout) findViewById(R.id.changeModContainer);
            //category
        changeModCategoryEditBox = (LinearLayout) findViewById(R.id.changeModCategoryEditBox);
        changeModCategory = (LinearLayout) findViewById(R.id.changeModCategory);
        changeModCurrentCategory = (TextView) findViewById(R.id.changeModCurrentCategory);
        changeModCategoryList = (LinearLayout) findViewById(R.id.changeModCategoryList);
                //searchCategory
        changeCategoryEt = (EditText) findViewById(R.id.changeCategoryEt);
        SearchCategoryTextWatcher searchCategoryWatcher = new SearchCategoryTextWatcher(dbHelper, this, changeModCategoryList, changeCategoryEt);
        changeCategoryEt.addTextChangedListener(searchCategoryWatcher);
            //tag
        changeModTagEditBox = (LinearLayout) findViewById(R.id.changeModTagEditBox);
        changeModTag = (LinearLayout) findViewById(R.id.changeModTag);
        changeModTagExistList = (LinearLayout) findViewById(R.id.changeModTagExistList);
        changeModTagList = (FlowLayout) findViewById(R.id.changeModTagList);
                //searchTag
        changeTagEt = (EditText) findViewById(R.id.changeTagEt);
        SearchTagTextWatcher searchTagWatcher = new SearchTagTextWatcher(dbHelper, this, changeModTagList, changeTagEt);
        changeTagEt.addTextChangedListener(searchTagWatcher);
            //spot
        changeModSpotViewContainer = (RelativeLayout) findViewById(R.id.changeModSpotViewContainer);
        changeModCurrentSpotViewContainer = (RelativeLayout) findViewById(R.id.changeModCurrentSpotViewContainer);
        changeModSpot = (LinearLayout) findViewById(R.id.changeModSpot);
        changeModSpotCurrentContainer = (LinearLayout) findViewById(R.id.changeModSpotCurrentContainer);
        changeModSpotIcContainer = (LinearLayout) findViewById(R.id.changeModSpotIcContainer);

        //changeModListener
        changeModCloseZone = (LinearLayout) findViewById(R.id.changeModCloseZone);
        changeModCloseZone.setOnClickListener(this);
        changeBtn = (Button) findViewById(R.id.changeBtn);
        changeBtn.setOnClickListener(this);
            //category
        changeCategoryAddBtn = (RelativeLayout) findViewById(R.id.changeCategoryAddBtn);
        changeCategoryAddBtn.setOnClickListener(this);
        changeModClearCategory = (LinearLayout) findViewById(R.id.changeModClearCategory);
        changeModClearCategory.setOnClickListener(this);
            //tag
        changeTagAddBtn = (RelativeLayout) findViewById(R.id.changeTagAddBtn);
        changeTagAddBtn.setOnClickListener(this);

        //popup
        isPopupOpen = false;
        popupNotificationContainer = (LinearLayout) findViewById(R.id.popupNotificationContainer);
        popupNotificationContainer.setOnClickListener(this);
        popupRemoveBtnYes = (Button) findViewById(R.id.popupRemoveBtnYes);
        popupRemoveBtnYes.setOnClickListener(this);
        popupRemoveBtnNo = (Button) findViewById(R.id.popupRemoveBtnNo);
        popupRemoveBtnNo.setOnClickListener(this);

        //changeModSizeCorrect
        RelativeLayout.LayoutParams closeZoneParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int)changeCloseYSizeFloat);
        changeModCloseZone.setLayoutParams(closeZoneParams);

        RelativeLayout.LayoutParams changeModParams = (RelativeLayout.LayoutParams) changeModMenu.getLayoutParams();
        changeModParams.height = (int)changeMenuYSizeFloat;
        changeModParams.bottomMargin = (int)(-changeMenuYSizeFloat);
        changeModMenu.setLayoutParams(changeModParams);

        if(screenYSize > screenXSize) {
            LinearLayout.LayoutParams currentCategoryParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(40));
            currentCategoryParams.leftMargin = (int)dpToPixel(16);
            changeModCurrentCategory.setLayoutParams(currentCategoryParams);

            FrameLayout.LayoutParams tagExistListParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(40));
            tagExistListParams.topMargin = (int)dpToPixel(6);
            changeModTagExistList.setLayoutParams(tagExistListParams);

            LinearLayout.LayoutParams categoryEditBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(60));
            changeModCategoryEditBox.setLayoutParams(categoryEditBoxParams);

            LinearLayout.LayoutParams tagEditBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(60));
            changeModTagEditBox.setLayoutParams(tagEditBoxParams);
        } else {
            LinearLayout.LayoutParams currentCategoryParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(24));
            currentCategoryParams.leftMargin = (int)dpToPixel(16);
            changeModCurrentCategory.setLayoutParams(currentCategoryParams);

            FrameLayout.LayoutParams tagExistListParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(40));
            tagExistListParams.topMargin = (int)dpToPixel(6);
            changeModTagExistList.setLayoutParams(tagExistListParams);

            LinearLayout.LayoutParams categoryEditBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(50));
            changeModCategoryEditBox.setLayoutParams(categoryEditBoxParams);

            LinearLayout.LayoutParams tagEditBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(50));
            changeModTagEditBox.setLayoutParams(tagEditBoxParams);
        }

        cacheModZoneNumber = 1;
        loadCurrentCategory();

        //saveTextWatcher
        NoteSaveTextWatcher noteSaveTextWatcher = new NoteSaveTextWatcher(dbHelper, this, this, notePresenter, noteTextEt);
        noteTextEt.addTextChangedListener(noteSaveTextWatcher);

        Log.d("log", "Current extras openCategory: " + openCategory);
        Log.d("log", "Current extras categoryNote: " + noteCategory);

        //spotSizeCorrect
        LinearLayout.LayoutParams spotViewContainerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)((changeMenuYSizeFloat - dpToPixel(55))/100*80));
        changeModSpotViewContainer.setLayoutParams(spotViewContainerParams);
        LinearLayout.LayoutParams currentSpotViewContainerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)((changeMenuYSizeFloat - dpToPixel(55))/100*20));
        changeModCurrentSpotViewContainer.setLayoutParams(currentSpotViewContainerParams);

        RelativeLayout.LayoutParams spotBoxParams = new RelativeLayout.LayoutParams((int)spotRowBuilderMain.getBoxWidth(), (int)spotRowBuilderMain.getBoxHeight());
        spotBoxParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        spotBoxParams.addRule(RelativeLayout.CENTER_VERTICAL);
        int spotBoxMargin = (int)spotRowBuilderMain.getSpotBoxMargin();
        int spotBoxPadding = (int)spotRowBuilderMain.getSpotBoxPadding();
        spotBoxParams.setMargins(spotBoxMargin, 0, spotBoxMargin, 0);
        changeModSpotIcContainer.setLayoutParams(spotBoxParams);
        changeModSpotIcContainer.setPadding(spotBoxPadding, spotBoxPadding, spotBoxPadding, spotBoxPadding);

        //fillChangeModArrays
        changeModContainersList.add(changeModTag);
        changeModContainersList.add(changeModCategory);
        changeModContainersList.add(changeModSpot);
        changeModContainersList.add(popupNotificationContainer);

        changeModBtnsList.add(barTagBtn);
        changeModBtnsList.add(barCategoryBtn);
        changeModBtnsList.add(barSpotBtn);
        changeModBtnsList.add(barRemoveBtn);

        //swipeZone
        swipeZone = (LinearLayout) findViewById(R.id.swipeZone);
        swipeZone.setOnTouchListener(new NoteActivityTouchListener(this, this));

        //theme
        themeWrapsList = new ArrayList<>();

        themePref = getSharedPreferences("theme", Context.MODE_PRIVATE);

        if(!themePref.contains(ThemeConstants.BACK_DARK)) {
            Map<String, String> colorsMap = new ThemeDefaultMock().getColorMap();

            SharedPreferences.Editor editor = themePref.edit();

            for(Map.Entry<String, String> entry : colorsMap.entrySet())
                editor.putString(entry.getKey(), entry.getValue());

            editor.putString("test", "#ff0000");

            editor.apply();
        }

        createMapColor();
        createThemeWrapsList();
        buildTheme(new ThemeSetter(themeWrapsList, mapColor));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //mainScreen
            case R.id.backToMainBtn :
                backToMainActivity();

                break;
            case R.id.changeBtn :
                isChangeModOn = true;
                switchChangeModContainer(cacheModZoneNumber);
                break;
            case R.id.removeBtn :
                switchPopupContainer();
                break;
            //bar
            case R.id.barCategoryBtn :
                switchChangeModContainer(1);
                break;
            case R.id.barTagBtn :
                switchChangeModContainer(0);
                break;
            case R.id.barSpotBtn :
                switchChangeModContainer(2);
                break;
            case R.id.barRemoveBtn :
                switchPopupContainer();
                break;
            //changeMod
            case R.id.changeModCloseZone :
                closeChangeMod();
                break;
                //category
            case R.id.changeCategoryAddBtn :
                changeModUpdateCategory(changeCategoryEt.getText().toString());
                break;
            case R.id.changeModClearCategory :
                changeModRemoveCategory();
                break;
                //tag
            case R.id.changeTagAddBtn :
                changeModUpdateTag(changeTagEt.getText().toString());
                break;
            //popupRemove
            case R.id.popupNotificationContainer :
                if(isPopupOpen) switchPopupContainer();
                break;
            case R.id.popupRemoveBtnYes :
                removeEvent();
                break;
            case R.id.popupRemoveBtnNo :
                if(isPopupOpen) switchPopupContainer();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backToMainActivity();

        super.onBackPressed();
    }

    @Override
    public void showError(ToastErrors key) {
        String toastStr = "";

        if(key == ToastErrors.CATEGORY_EXIST) {
            toastStr = getResources().getString(R.string.error_category_exist);
        } else if(key == ToastErrors.LONGER) {
            toastStr = getResources().getString(R.string.error_44_longer);
        } else if(key == ToastErrors.NAME_IS_EMPTY) {
            toastStr = getResources().getString(R.string.error_name_is_empty);
        } else if(key == ToastErrors.NOT_VALID_TAG_NAME) {
            toastStr = getResources().getString(R.string.error_tag_name);
        } else if(key == ToastErrors.INVALID_CHARACTER_COMBINATION) {
            toastStr = getResources().getString(R.string.error_invalid_symbol);
        }

        Toast toast = Toast.makeText(this, toastStr, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void showToast(ToastWarnings key, String subStr) {
        String toastStr = "";

        if(key == ToastWarnings.ADD_CATEGORY_WITH_NAME) {
            try {
                toastStr = String.format("%s %s", getResources().getString(R.string.toast_add_category_with_name), subStr);
            } catch (NullPointerException e) {}
        } else if(key == ToastWarnings.ADD_TAG_WITH_NAME) {
            try {
                toastStr = String.format("%s %s", getResources().getString(R.string.toast_add_tag_with_name), subStr);
            } catch (NullPointerException e) {}
        } else if(key == ToastWarnings.CATEGORIES_REMOVED) {
            toastStr = getResources().getString(R.string.toast_categories_remove);
        } else if(key == ToastWarnings.CATEGORY_REMOVED) {
            toastStr = getResources().getString(R.string.toast_category_remove);
        } else if(key == ToastWarnings.NOTE_REMOVED) {
            toastStr = getResources().getString(R.string.toast_note_remove);
        } else if(key == ToastWarnings.NOTES_REMOVED) {
            toastStr = getResources().getString(R.string.toast_notes_remove);
        } else if(key == ToastWarnings.TAG_REMOVED) {
            toastStr = getResources().getString(R.string.toast_remove_tag);
        } else if(key == ToastWarnings.TAGS_REMOVED) {
            toastStr = getResources().getString(R.string.toast_remove_tags);
        }

        Toast toast = Toast.makeText(this, toastStr, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void switchChangeModContainer(int number) {
        if(isChangeModOn && number != cacheChangeMod) {
            Animation selectOutAnim = AnimationUtils.loadAnimation(this, R.anim.changemod_bar_select_ic_out_anim);

            //openBox
            if(!isChangeContainerShow && number != 3 && number >= 0) {
                changeModOpenAnim(true);

                isChangeContainerShow = true;
            }

            //clean previous params
            if(number != 3) {
                if(number >= 0) {
                    for (View v : changeModContainersList)
                        v.setVisibility(View.GONE);
                }

                for (View v : changeModBtnsList) {
                    LinearLayout selectIc = v.findViewWithTag("ic_select");
                    selectIc.setAnimation(selectOutAnim);
                    selectIc.setVisibility(View.GONE);
                    v.findViewWithTag("ic").getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_IC_COLOR), PorterDuff.Mode.SRC_IN);
                }
            }

            //open mod or close changeMod
            if(number >= 0 && number <= 3) {
                changeModLoadList((LinearLayout) changeModContainersList.get(number));

                if(number != 3) {
                    Animation selectAnim = AnimationUtils.loadAnimation(this, R.anim.changemod_bar_select_ic_in_anim);
                    LinearLayout icSelect = changeModBtnsList.get(number).findViewWithTag("ic_select");
                    icSelect.setVisibility(View.VISIBLE);
                    icSelect.setAnimation(selectAnim);

                    changeModBtnsList.get(number).findViewWithTag("ic").getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_1), PorterDuff.Mode.SRC_IN);

                    changeModContainersList.get(number).setVisibility(View.VISIBLE);

                    cacheModZoneNumber = number;
                    cacheChangeMod = number;
                }
            } else {
                /*if(number == -2) changeModOpenSwitcher(false);
                else changeModOpenAnim(false);*/
                changeModOpenAnim(false);

                changeModTagExistList.setVisibility(View.GONE);
                isChangeContainerShow = false;
            }
        }
    }

    public void changeModLoadList(LinearLayout zone) {
        switch(zone.getId()) {
            case R.id.changeModCategory :
                changeModLoadCategory(this, changeModCategoryList, notePresenter.getAllCategory());
                break;
            case R.id.changeModTag :
                changeModLoadTags(this, changeModTagList, notePresenter.getAllTag());
                changeModLoadExistTags(this, changeModTagExistList);
                break;
            case R.id.changeModSpot :
                changeModLoadSpotList(this, changeModSpotIcContainer);
                changeModLoadCurrentSpot(this, changeModSpotCurrentContainer);
                break;
            case R.id.popupNotificationContainer :
                switchPopupContainer();
                break;
        }
    }

    public void closeChangeMod() {
        if(isChangeModOn) {
            switchChangeModContainer(-2);

            changeModTagExistList.setVisibility(View.GONE);
        }

        isChangeModOn = false;
        isChangeContainerShow = false;
    }

    public void loadCurrentCategory() {
        try {
            Log.d("log", "loadCurrentCategory: noteCategory = " + noteCategory);
            Log.d("log", "loadCurrentCategory: openCategory = " + openCategory);

            if(!noteCategory.equalsIgnoreCase("")) {
                changeModCurrentCategory.setText(noteCategory);
                changeModCurrentCategory.setVisibility(View.VISIBLE);

                if(screenYSize > screenXSize) {
                    if(noteCategory.length() >= 30) changeModCurrentCategory.setTextSize(11);
                    else changeModCurrentCategory.setTextSize(15);
                }
            } else {
                changeModCurrentCategory.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            Log.d("log", "NullPointerException in loadCurrentCategory");
        }
    }

    public void changeModLoadCategory(Context context, LinearLayout container, List<Category> categories) {
        container.removeAllViews();

        for (Category c : categories) {
            String categoryName = c.getName();

            //container
            LinearLayout categoryContainer = new LinearLayout(context);
            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            categoryContainer.setLayoutParams(containerParams);

            //textBox
            LinearLayout textBoxView = new LinearLayout(context);
            LinearLayout.LayoutParams textBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(40));
            textBoxParams.setMargins((int)dpToPixel(10), (int)dpToPixel(1), (int)dpToPixel(10), (int)dpToPixel(10));
            textBoxView.setElevation((int)dpToPixel(1));
            textBoxView.setTranslationZ((int)dpToPixel(3));
            textBoxView.setBackground(getResources().getDrawable(R.drawable.back_1_rounded_ripple));
            textBoxView.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_1), PorterDuff.Mode.SRC_IN);

            //textMarginBox
            LinearLayout textMargin = new LinearLayout(context);
            LinearLayout.LayoutParams textMarginParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textMarginParams.gravity = Gravity.CENTER_VERTICAL;
            textMarginParams.leftMargin = (int)dpToPixel(16);
            textMarginParams.bottomMargin = (int)dpToPixel(2);
            textMargin.setLayoutParams(textMarginParams);

            //text
            TextView nameView = new TextView(context);
            ViewGroup.LayoutParams nameParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            nameView.setLayoutParams(nameParams);
            nameView.setTextSize(12);
            nameView.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));
            nameView.setText(categoryName);
            nameView.setTag("categoryName");

            //onClick
            categoryContainer.setFocusable(true);
            categoryContainer.setClickable(true);

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView nameView = v.findViewWithTag("categoryName");
                    changeModUpdateCategory(nameView.getText().toString());
                }
            };

            categoryContainer.setOnClickListener(onClickListener);

            //sizeCorrect
            if(screenYSize > screenXSize) {
                if(categoryName.length() >= 30) nameView.setTextSize(10);
                else nameView.setTextSize(12);
            } else {
                textBoxParams = new LinearLayout.LayoutParams(screenXSize/100*80, (int)dpToPixel(40));
                textBoxParams.setMargins((int)dpToPixel(10), (int)dpToPixel(1), (int)dpToPixel(10), (int)dpToPixel(10));
            }

            if(categories.indexOf(c) == 0) textBoxParams.setMargins((int)dpToPixel(10), (int)dpToPixel(10), (int)dpToPixel(10), (int)dpToPixel(10));
            textBoxView.setLayoutParams(textBoxParams);

            //implement
            textMargin.addView(nameView);
            textBoxView.addView(textMargin);
            categoryContainer.addView(textBoxView);
            container.addView(categoryContainer);
        }
    }

    public void changeModUpdateCategory(String categoryName) {
        if(!categoryName.equalsIgnoreCase("")) {
            notePresenter.checkCategoryExist(categoryName);

            if(isExist) {
                if(!noteCategory.equals(categoryName)) notePresenter.updateNoteAmount(noteCategory, categoryName);
            }

            noteCategory = categoryName;

            if(isExist)
                notePresenter.updateNote(id, text, notePresenter.getTagStr(tags), categoryName, new Date(), spot);
        }

        changeCategoryEt.setText("");
        changeModLoadCategory(this, changeModCategoryList, notePresenter.getAllCategory());
        loadCurrentCategory();
    }

    public void changeModRemoveCategory() {
        if(isExist) {
            notePresenter.updateNoteAmount(noteCategory, "");
            notePresenter.updateNote(id, text, notePresenter.getTagStr(tags), "", new Date(), spot);
        }

        noteCategory = "";

        loadCurrentCategory();
    }

    public void changeModLoadTags(Context context, FlowLayout container, List<Tag> tags) {
        container.removeAllViews();

        if(tags.size() > 0) {
            for (Tag t : tags) {
                String tagName = t.getName();

                //tagContainer
                LinearLayout tagContainer = new LinearLayout(context);
                tagContainer.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams tagContainerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tagContainer.setLayoutParams(tagContainerParams);

                //tagBox
                LinearLayout tagBox = new LinearLayout(context);
                LinearLayout.LayoutParams tagBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tagBoxParams.setMargins(0, 0, (int)dpToPixel(14), (int)dpToPixel(14));
                tagBox.setLayoutParams(tagBoxParams);
                tagBox.setPadding((int)dpToPixel(15), 0, (int)dpToPixel(15), (int)dpToPixel(3));
                tagBox.setBackgroundResource(R.drawable.tag_light_shape_ripple);
                tagBox.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_3), PorterDuff.Mode.SRC_IN);
                tagBox.setElevation((int)dpToPixel(0));
                tagBox.setTranslationZ((int)dpToPixel(2));
                tagBox.setTag("tagBox");

                //tagName
                TextView tagView = new TextView(context);
                ViewGroup.LayoutParams tagViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tagView.setLayoutParams(tagViewParams);
                tagView.setText(tagName);
                tagView.setTextSize(14);
                tagView.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));
                tagView.setTag("tagName");

                //onClickListener
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView nameView = v.findViewWithTag("tagName");
                        changeModUpdateTag(nameView.getText().toString());
                    }
                };

                tagBox.setOnClickListener(onClickListener);

                //sizeCorrect
                if(screenYSize > screenXSize) {
                    if(tagName.length() >= 30) {
                        tagView.setTextSize(10);
                        tagBox.setPadding((int)dpToPixel(15), (int)dpToPixel(4), (int)dpToPixel(15), (int)dpToPixel(5));
                    } else {
                        tagView.setTextSize(14);
                        tagBox.setPadding((int)dpToPixel(15), 0, (int)dpToPixel(15), (int)dpToPixel(3));
                    }
                }

                //implement
                tagBox.addView(tagView);
                tagContainer.addView(tagBox);
                container.addView(tagContainer);
            }
        }
    }

    public void changeModLoadExistTags(Context context, LinearLayout container) {
        container.removeAllViews();

        List<String> tempParseTags = new ArrayList<>();

        for (Tag t : tags)
            if(!t.getName().equalsIgnoreCase("")) tempParseTags.add(t.getName());

        if(tempParseTags.size() > 0) container.setVisibility(View.VISIBLE);
        else container.setVisibility(View.GONE);

        for (String s : tempParseTags) {
            if(!s.equalsIgnoreCase("")) {
                //tagContent
                LinearLayout tagContent = new LinearLayout(context);
                tagContent.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams tagContentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, (int)dpToPixel(22));
                tagContentParams.gravity = Gravity.CENTER_VERTICAL;
                tagContentParams.setMargins((int)dpToPixel(6), 0, (int)dpToPixel(6), 0);
                tagContent.setBackground(getResources().getDrawable(R.drawable.tag_shape));
                tagContent.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_3), PorterDuff.Mode.SRC_IN);
                tagContent.setElevation((int)dpToPixel(2));
                tagContent.setTranslationZ((int)dpToPixel(4));
                tagContent.setLayoutParams(tagContentParams);

                //tagBox
                RelativeLayout tagBox = new RelativeLayout(context);
                LinearLayout.LayoutParams tagBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                tagBox.setLayoutParams(tagBoxParams);
                tagBox.setPadding((int)dpToPixel(10), 0, (int)dpToPixel(10), 0);
                tagBox.setBackgroundResource(R.drawable.left_rounded_tag_shape);
                tagBox.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_3), PorterDuff.Mode.SRC_IN);

                //tagName
                TextView tagView = new TextView(context);
                RelativeLayout.LayoutParams tagViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tagViewParams.addRule(RelativeLayout.CENTER_VERTICAL);
                tagView.setLayoutParams(tagViewParams);
                tagView.setText(s);
                tagView.setTextSize(12);
                tagView.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));
                tagView.setTag("tagName");

                //closeBox
                LinearLayout closeBox = new LinearLayout(context);
                LinearLayout.LayoutParams closeBoxParams = new LinearLayout.LayoutParams((int)dpToPixel(22), (int)dpToPixel(22));
                closeBox.setLayoutParams(closeBoxParams);
                closeBox.setPadding((int)dpToPixel(4), (int)dpToPixel(4), (int)dpToPixel(4), (int)dpToPixel(4));
                closeBox.setBackgroundResource(R.drawable.tag_close_ripple);
                closeBox.getBackground().setColorFilter(mapColor.get(ThemeConstants.ERROR_COLOR), PorterDuff.Mode.SRC_IN);
                closeBox.setTag("closeBox");

                //closeIc
                ImageView closeIc = new ImageView(context);
                ViewGroup.LayoutParams closeIcParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                closeIc.setLayoutParams(closeIcParams);
                closeIc.setBackgroundResource(R.drawable.ic_cross);
                closeIc.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_DARK), PorterDuff.Mode.SRC_IN);

                //onClick
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayout parent = (LinearLayout) v.getParent();
                        TextView textTag = (TextView) parent.findViewWithTag("tagName");

                        changeModRemoveTag(textTag.getText().toString());
                    }
                };
                closeBox.setOnClickListener(onClickListener);

                //sizeCorrect
                if(s.length() >= 30) {
                    tagView.setTextSize(10);
                } else {
                    tagView.setTextSize(12);
                }

                //implement
                tagBox.addView(tagView);
                tagContent.addView(tagBox);
                closeBox.addView(closeIc);
                tagContent.addView(closeBox);
                container.addView(tagContent);
            }
        }
    }

    public void changeModUpdateTag(String tagStr) {
        if(!tagStr.equalsIgnoreCase("")) {
            if(notePresenter.isValidateTagString(tagStr)) {
//                if(!text.equalsIgnoreCase("")) {
                //CRUD
                if(isExist) {
                    List<Tag> tagsByName = notePresenter.getTagByName(tagStr);

                    if(tagsByName.size() == 0)
                        notePresenter.addTag(tagStr, true);

                    String tag = "";
                    boolean isContains = false;

                    if(tags.size() > 0) {
                        for (Tag t : tags){
                            String tagName = t.getName();
                            if(tagName.equals(tagStr)) isContains = true;
                            if(!tagName.equalsIgnoreCase("")) tag += tagName + "@@";
                        }
                    }

                    tag += tagStr;

                    if(!isContains && isExist)
                        notePresenter.updateNote(id, text, tag, noteCategory, new Date(), spot);
                } else {
                    showToast(ToastWarnings.ADD_TAG_WITH_NAME, tagStr);
                }

                //setNewTag
                addNewTagList(tagStr);

                //view
                changeModLoadTags(this, changeModTagList, notePresenter.getAllTag());
                changeModLoadExistTags(this, changeModTagExistList);
                changeTagEt.setText("");
//                }
            } else {
                showError(ToastErrors.NOT_VALID_TAG_NAME);
            }
        }
    }

    private void addNewTagList(String newTagName) {
        boolean isContains = false;

        for(Tag t : tags) {
            String tagName = t.getName();
            if(!tagName.equalsIgnoreCase("") && tagName.equals(newTagName)) {
                isContains = true;
                break;
            }
        }

        Tag newTag = new Tag();
        newTag.setName(newTagName);

        if(!isContains) tags.add(newTag);
    }

    public void changeModRemoveTag(String tagName) {
        if(!tagName.equalsIgnoreCase("")) {
            boolean isContains = false;

            for(Tag t : tags) {
                String name = t.getName();
                if(!name.equalsIgnoreCase("") && name.equals(tagName)) isContains = true;
            }

            if(isContains) {
                int removeIndex = 0;

                for(Tag t : tags)
                    if(t.getName().equals(tagName)) removeIndex = tags.indexOf(t);

                List<Note> notesByTag = notePresenter.getNoteByTag(tagName);
                if(notesByTag.size() <= 1) notePresenter.removeTag(tagName);
                tags.remove(removeIndex);

                if(isExist) notePresenter.updateNote(id, text, notePresenter.getTagStr(tags), noteCategory, new Date(), spot);
            }

            changeModLoadTags(this, changeModTagList, notePresenter.getAllTag());
            changeModLoadExistTags(this, changeModTagExistList);
        }
    }

    private void changeModLoadSpotList(Context context, LinearLayout container) {
        container.removeAllViews();

        int i = 0;
        int rowCount = 0;
        int rowWeight = spotRowBuilderMain.getSpotRowWeight();
        int rowAmount = spotRowBuilderMain.getRowAmount();
        int spotA = (int)spotRowBuilderMain.getSpotA();

        List<LinearLayout> rowList = new ArrayList<>();

        for (int j = 0; j < rowAmount; j++) {
            LinearLayout row = new LinearLayout(context);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, spotA);
            row.setLayoutParams(rowParams);

            rowList.add(row);
        }

        for(String s : spotIcList) {
            //box
            ImageView icBox = new ImageView(context);

            try {
                InputStream inputStream = getAssets().open(s);
                Bitmap bm = BitmapFactory.decodeStream(inputStream);
                icBox.setImageBitmap(bm);
                inputStream.close();
            } catch (IOException e) {}

            ViewGroup.LayoutParams boxParams = new ViewGroup.LayoutParams(spotA, spotA);
            icBox.setLayoutParams(boxParams);
            icBox.setTag(s);

            //onClick
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String spotPath = v.getTag().toString();

                    if(spotPath.equalsIgnoreCase("spot_clean.png")) changeModUpdateSpot("");
                    else changeModUpdateSpot(spotPath);
                }
            };

            icBox.setOnClickListener(onClickListener);

            //implement
            rowList.get(rowCount).addView(icBox);

            i++;
            if(i >= rowWeight) {
                i = 0;
                rowCount++;
            }
        }

        for (View v : rowList)
            container.addView(v);
    }

    /*public void changeModLoadCurrentSpot(Context context, LinearLayout container) {
        container.removeAllViews();

        if(!spot.equalsIgnoreCase("")) {
            int dp44 = (int)dpToPixel(44);
            int dp2 = (int)dpToPixel(2);

            //spotBox
            LinearLayout spotBox = new LinearLayout(context);
            LinearLayout.LayoutParams boxParams = new LinearLayout.LayoutParams(dp44, dp44);
            spotBox.setLayoutParams(boxParams);
            spotBox.setPadding(dp2, 0, dp2, (int)dpToPixel(8));

            //spotView
            ImageView spotView = new ImageView(context);
//            spotView.setImageResource(spot);

            try {
                InputStream inputStream = getAssets().open(spot);
                Bitmap bm = BitmapFactory.decodeStream(inputStream);
                spotView.setImageBitmap(bm);
                inputStream.close();
            } catch (IOException e) {}

            ViewGroup.LayoutParams spotParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            spotView.setLayoutParams(spotParams);

            //addToContainer
            spotBox.addView(spotView);
            container.addView(spotBox);
        }
    }*/

    private void changeModLoadCurrentSpot(Context context, LinearLayout container) {
        container.removeAllViews();

        TransitionManager.beginDelayedTransition(container, new TransitionSet()
                .setDuration(250)
                .addTransition(new Scale(0.4f))
                .addTransition(new Fade())
                .setInterpolator(new AccelerateDecelerateInterpolator()));

        if(!spot.equalsIgnoreCase("")) {
            int topPadding = (int)changeMenuYSizeFloat/100*3;
            int a = (int)changeMenuYSizeFloat/100*17;

            //spotBox
            LinearLayout spotBox = new LinearLayout(context);
            LinearLayout.LayoutParams boxParams = new LinearLayout.LayoutParams(a, a);
            boxParams.gravity = Gravity.BOTTOM;
            spotBox.setLayoutParams(boxParams);
            spotBox.setPadding(0, topPadding, 0, 0);

            //spotView
            ImageView spotView = new ImageView(context);

            try {
                InputStream inputStream = getAssets().open(spot);
                Bitmap bm = BitmapFactory.decodeStream(inputStream);
                spotView.setImageBitmap(bm);
                inputStream.close();
            } catch (IOException e) {}

            ViewGroup.LayoutParams spotParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            spotView.setLayoutParams(spotParams);

            //addToContainer
            spotBox.addView(spotView);
            container.addView(spotBox);
        }
    }

    public void changeModUpdateSpot(String spotId) {
        Log.d("log", "changeModUpdateSpot, id = " + id);
        Log.d("log", String.format("SpotPath: %s", spotId));

        if(isExist)
            notePresenter.updateNote(id, text, notePresenter.getTagStr(tags), noteCategory, new Date(), spotId);

        spot = spotId;

        changeModLoadCurrentSpot(this, changeModSpotCurrentContainer);
    }

    public void removeEvent() {
        if(isExist) {
            notePresenter.removeNote(id, noteCategory, notePresenter.getTagStr(tags), true);

            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        } else {
            Intent mainIntent = new Intent(this, MainActivity.class);
            String tempCategory = openCategory;
            if(tempCategory.equalsIgnoreCase("")) tempCategory = getResources().getString(R.string.all_notes);

            mainIntent.putExtra("cacheCategory", tempCategory);

            for(Tag t : tags) {
                String tagName = t.getName();
                List<Note> notesByTag = notePresenter.getNoteByTag(tagName);
                if(notesByTag.size() == 0) notePresenter.removeTag(tagName);
            }

            startActivity(mainIntent);
        }
    }

    @Override
    public void backToMainActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        String tempCategory;

        tempCategory = openCategory;

        if(tempCategory.equalsIgnoreCase("")) tempCategory = getResources().getString(R.string.all_notes);

        mainIntent.putExtra("cacheCategory", tempCategory);

        startActivity(mainIntent);
    }

    @Override
    public void runCategorySearch(LinearLayout container, List<Category> categories) {
        changeModLoadCategory(this, container, categories);
    }

    @Override
    public void runTagSearch(FlowLayout container, List<Tag> tags) {
        changeModLoadTags(this, container, tags);
    }

    private void switchPopupContainer () {
        if(isExist) {
            RelativeLayout btnBox = (RelativeLayout) changeModBtnsList.get(3);
            AppCompatImageView ic = btnBox.findViewWithTag("ic");
            RelativeLayout removeContainer = popupNotificationContainer.findViewWithTag("remove_container");

            TransitionManager.beginDelayedTransition(popupNotificationContainer, new TransitionSet()
                    .setDuration(300)
                    .addTransition(new Fade()));

            if(!isPopupOpen) {
                Animation popupShowAnim = AnimationUtils.loadAnimation(this, R.anim.popup_show_anim);

                ic.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_1), PorterDuff.Mode.SRC_IN);

                popupNotificationContainer.setVisibility(View.VISIBLE);
                removeContainer.setAnimation(popupShowAnim);

                isPopupOpen = true;

                tempCacheChangeMod = cacheChangeMod;
                cacheChangeMod = 3;
            } else {
                Animation popupHideAnim = AnimationUtils.loadAnimation(this, R.anim.popup_hide_anim);

                ic.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_IC_COLOR), PorterDuff.Mode.SRC_IN);

                popupNotificationContainer.setVisibility(View.GONE);
                removeContainer.setAnimation(popupHideAnim);

                isPopupOpen = false;

                cacheChangeMod = tempCacheChangeMod;
                tempCacheChangeMod = -1;
            }
        } else removeEvent();
    }

    @Override
    public float dpToPixel(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    @Override
    public Note getNoteObject() {
        Note note = new Note();

        //text
        String textStr = noteTextEt.getText().toString();
        text = textStr;
        note.setText(textStr);

        //spot
        try {
            if(!spot.equalsIgnoreCase("")) note.setSpot(spot);
            else note.setSpot("");
        } catch (NullPointerException e) {
            note.setSpot("");
        }

        //category
        if(!noteCategory.equalsIgnoreCase("")) {
            note.setCategory(noteCategory);
        } else {
            note.setCategory(openCategory);
        }

        //tags
        note.setTags(tags);

        //id
        note.setId(id);

        return note;
    }

    @Override
    public boolean getIsExist() {
        return isExist;
    }

    @Override
    public void setIsExist(boolean is) {
        isExist = is;

        if(is) {
            List<Note> notes = notePresenter.getNoteByText(noteTextEt.getText().toString());

            if(notes.size() == 1) {
                id = notes.get(0).getId();
            } else if (notes.size() > 1) {
                id = notes.get(notes.size()-1).getId();
            }
        } else {
            id = 0;
        }
    }

    @Override
    public void checkTagSave() {
        for(Tag t : tags) {
            String tagName = t.getName();

            if(!tagName.equalsIgnoreCase("")) {
                List<Tag> tagsByName = notePresenter.getTagByName(tagName);
                if(tagsByName.size() == 0) {
                    notePresenter.addTag(tagName, false);
                }
            }
        }
    }

    private void changeModOpenAnim(boolean toOpen) {
        if(!isOpenAnim) {
            isOpenAnim = true;

            TransitionManager.beginDelayedTransition(changeModMenu, new ChangeBounds()
                    .setDuration(400)
                    .setInterpolator(new AccelerateDecelerateInterpolator()));

            TransitionManager.beginDelayedTransition(changeModCloseZone, new TransitionSet()
                    .setDuration(200)
                    .addTransition(new Fade())
                    .addTransition(new Scale(0.1f)));

            changeModOpenSwitcher(toOpen);
        }
    }

    private void changeModOpenSwitcher(boolean toOpen) {
        if(toOpen) {
            changeModCloseZone.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams containerParams = (RelativeLayout.LayoutParams) changeModMenu.getLayoutParams();
            containerParams.bottomMargin = 0;
            changeModMenu.setLayoutParams(containerParams);
        } else {
            changeModCloseZone.setVisibility(View.GONE);
            RelativeLayout.LayoutParams containerParams = (RelativeLayout.LayoutParams) changeModMenu.getLayoutParams();
            containerParams.bottomMargin = (int)(-changeMenuYSizeFloat);
            changeModMenu.setLayoutParams(containerParams);

            cacheChangeMod = -1;
        }

        Thread waitThread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(150);
                    isOpenAnim = false;
                    this.interrupt();
                } catch (InterruptedException e) {}
            }
        };

        waitThread.start();
        waitThread.run();
    }

    //Theme
    @Override
    public void createThemeWrapsList() {
            //changeMod
        //barContainer
        themeWrapsList.add(new ThemeViewWrap(barContainer, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK));
        //barBtnIc x 4//barSelectIc x 4
        for (View v : changeModBtnsList) {
            themeWrapsList.add(new ThemeViewWrap(v.findViewWithTag("ic"), ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_IC_COLOR));
            themeWrapsList.add(new ThemeViewWrap(v.findViewWithTag("ic_select"), ThemeWrapType.CLASSIC_VIEW, ThemeConstants.AC_1));
        }
        //barBorders x 3
        themeWrapsList.add(new ThemeViewWrap(changeModBarBorder1, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.DEEP_DARK));
        themeWrapsList.add(new ThemeViewWrap(changeModBarBorder2, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.DEEP_DARK));
        themeWrapsList.add(new ThemeViewWrap(changeModBarBorder3, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.DEEP_DARK));
        //closeZone
        themeWrapsList.add(new ThemeViewWrap(changeModCloseZone, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        //bgc
        themeWrapsList.add(new ThemeViewWrap(changeModContainer, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_1));
        //TagEt
        themeWrapsList.add(new ThemeViewWrap(changeTagEt, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_3));
        //TagEtText
        themeWrapsList.add(new ThemeViewWrap(changeTagEt, ThemeWrapType.EDITTEXT_TEXT, ThemeConstants.TEXT_LIGHT));
        //TagEtText(hint)
        themeWrapsList.add(new ThemeViewWrap(changeTagEt, ThemeWrapType.EDITTEXT_HINT, ThemeConstants.HINT));
        //TagAddBtn
        themeWrapsList.add(new ThemeViewWrap(changeTagAddBtn, ThemeWrapType.RIPPLE_BOX, ThemeConstants.AC_1));
        //TagAddIc
        themeWrapsList.add(new ThemeViewWrap(changeTagAddIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_IC_COLOR));
        //clearTagAcBox
        themeWrapsList.add(new ThemeViewWrap(clearTagAcBox, ThemeWrapType.SHAPE_BOX, ThemeConstants.AC_1));
        //clearTagIc
        themeWrapsList.add(new ThemeViewWrap(clearTagIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK));
        //clearTagBox
        themeWrapsList.add(new ThemeViewWrap(changeModClearTag, ThemeWrapType.RIPPLE_BOX, ThemeConstants.BACK_DARK));
        //clearTagText
        themeWrapsList.add(new ThemeViewWrap(clearTagTextView, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_DARK));
        //CategoryEt
        themeWrapsList.add(new ThemeViewWrap(changeCategoryEt, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_3));
        //CategoryEtText
        themeWrapsList.add(new ThemeViewWrap(changeCategoryEt, ThemeWrapType.EDITTEXT_TEXT, ThemeConstants.TEXT_LIGHT));
        //CategoryEtText(hint)
        themeWrapsList.add(new ThemeViewWrap(changeCategoryEt, ThemeWrapType.EDITTEXT_HINT, ThemeConstants.HINT));
        //changeModCurrentCategory(text)
        themeWrapsList.add(new ThemeViewWrap(changeModCurrentCategory, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        //CategoryAddBtn
        themeWrapsList.add(new ThemeViewWrap(changeCategoryAddBtn, ThemeWrapType.RIPPLE_BOX, ThemeConstants.AC_1));
        //CategoryAddIc
        themeWrapsList.add(new ThemeViewWrap(changeCategoryAddIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_IC_COLOR));
        //clearCategoryAcBox
        themeWrapsList.add(new ThemeViewWrap(clearCategoryAcBox, ThemeWrapType.SHAPE_BOX, ThemeConstants.AC_1));
        //clearCategoryIc
        themeWrapsList.add(new ThemeViewWrap(clearCategoryIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK));
        //clearCategoryBox
        themeWrapsList.add(new ThemeViewWrap(changeModClearCategory, ThemeWrapType.RIPPLE_BOX, ThemeConstants.BACK_DARK));
        //clearCategoryText
        themeWrapsList.add(new ThemeViewWrap(clearCategoryTextView, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_DARK));
        //spotContainer
        themeWrapsList.add(new ThemeViewWrap(changeModSpotIcContainer, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_3));
        //popupCloseZone
        themeWrapsList.add(new ThemeViewWrap(popupRemoveBlurView, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_3));
        //popupRemoveBgc
        themeWrapsList.add(new ThemeViewWrap(popupRemoveBox, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_1));
        //removeTitleIc
        themeWrapsList.add(new ThemeViewWrap(popupRemoveIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_IC_COLOR));
        //removeTitle
        themeWrapsList.add(new ThemeViewWrap(popupRemoveTitle, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        //removeBorder
        themeWrapsList.add(new ThemeViewWrap(popupRemoveBorderTop, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        //removeYesBtn
        themeWrapsList.add(new ThemeViewWrap(popupRemoveBtnYes, ThemeWrapType.RIPPLE_BOX, ThemeConstants.AC_1));
        //removeYesText
        themeWrapsList.add(new ThemeViewWrap(popupRemoveBtnYes, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_LIGHT));
        //removeCancelBtn
        themeWrapsList.add(new ThemeViewWrap(popupRemoveBtnNo, ThemeWrapType.RIPPLE_BOX, ThemeConstants.BACK_DARK));
        //removeCancelText
        themeWrapsList.add(new ThemeViewWrap(popupRemoveBtnNo, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_DARK));

            //mainScreen
        //backToMainBtn (icon)
        themeWrapsList.add(new ThemeViewWrap(backToMainBtn, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK));
        //topline
        themeWrapsList.add(new ThemeViewWrap(topline, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_3));
        //changeBtn (btn_text)
        themeWrapsList.add(new ThemeViewWrap(changeBtn, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_LIGHT));
        //icRemove (ic)
        themeWrapsList.add(new ThemeViewWrap(icRemove, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK));
        //noteTextEt
        themeWrapsList.add(new ThemeViewWrap(noteTextEt, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_1));
        //noteTextEt (edittext_text)
        themeWrapsList.add(new ThemeViewWrap(noteTextEt, ThemeWrapType.EDITTEXT_TEXT, ThemeConstants.TEXT_LIGHT));

            //hide_zones
        //changeModTagHideZone1
        themeWrapsList.add(new ThemeViewWrap(changeModTagHideZone1, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));
        //changeModTagHideZone2
        themeWrapsList.add(new ThemeViewWrap(changeModTagHideZone2, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));
        //changeModCategoryHideZone1
        themeWrapsList.add(new ThemeViewWrap(changeModCategoryHideZone1, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));
        //changeModCategoryHideZone2
        themeWrapsList.add(new ThemeViewWrap(changeModCategoryHideZone2, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));
    }

    @Override
    public void buildTheme(ThemeSetter themeSetter) {
        themeSetter.startThemeSet();
    }

    @Override
    public void createMapColor() {
        mapColor.put(ThemeConstants.BACK_1, Color.parseColor(themePref.getString(ThemeConstants.BACK_1, testColor)));
        mapColor.put(ThemeConstants.BACK_2, Color.parseColor(themePref.getString(ThemeConstants.BACK_2, testColor)));
        mapColor.put(ThemeConstants.BACK_3, Color.parseColor(themePref.getString(ThemeConstants.BACK_3, testColor)));
        mapColor.put(ThemeConstants.BACK_4, Color.parseColor(themePref.getString(ThemeConstants.BACK_3, testColor)));
        mapColor.put(ThemeConstants.AC_1, Color.parseColor(themePref.getString(ThemeConstants.AC_1, testColor)));
        mapColor.put(ThemeConstants.AC_2, Color.parseColor(themePref.getString(ThemeConstants.AC_2, testColor)));
        mapColor.put(ThemeConstants.BACK_DARK, Color.parseColor(themePref.getString(ThemeConstants.BACK_DARK, testColor)));
        mapColor.put(ThemeConstants.BACK_DARK_2, Color.parseColor(themePref.getString(ThemeConstants.BACK_DARK_2, testColor)));
        mapColor.put(ThemeConstants.BACK_DARK_LIGHT, Color.parseColor(themePref.getString(ThemeConstants.BACK_DARK_LIGHT, testColor)));
        mapColor.put(ThemeConstants.DEEP_DARK, Color.parseColor(themePref.getString(ThemeConstants.DEEP_DARK, testColor)));
        mapColor.put(ThemeConstants.BACK_IC_COLOR, Color.parseColor(themePref.getString(ThemeConstants.BACK_IC_COLOR, testColor)));
        mapColor.put(ThemeConstants.ERROR_COLOR, Color.parseColor(themePref.getString(ThemeConstants.ERROR_COLOR, testColor)));
        mapColor.put(ThemeConstants.HINT, Color.parseColor(themePref.getString(ThemeConstants.HINT, testColor)));

        mapColor.put(ThemeConstants.TEXT_LIGHT, Color.parseColor(themePref.getString(ThemeConstants.TEXT_LIGHT, testColor)));
        mapColor.put(ThemeConstants.TEXT_DARK, Color.parseColor(themePref.getString(ThemeConstants.TEXT_DARK, testColor)));
    }
}
