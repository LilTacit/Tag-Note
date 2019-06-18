package com.mobile.kiril.tagnote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;

import com.mobile.kiril.tagnote.activeThemes.ThemeGift;
import com.mobile.kiril.tagnote.spotRowBuilder.SpotRowBuilderMain;
import com.mobile.kiril.tagnote.spotRowBuilder.SpotRowBuilderMenu;
import com.mobile.kiril.tagnote.themes.Collection;
import com.mobile.kiril.tagnote.themes.Collections;
import com.mobile.kiril.tagnote.themes.Theme;
import com.mobile.kiril.tagnote.themes.ThemeConstants;
import com.mobile.kiril.tagnote.themes.ThemeDefaultMock;
import com.mobile.kiril.tagnote.themes.ThemePreviewBuilder;
import com.mobile.kiril.tagnote.themes.ThemePreviewMovement;
import com.mobile.kiril.tagnote.themes.ThemeSetter;
import com.mobile.kiril.tagnote.themes.ThemeViewWrap;
import com.mobile.kiril.tagnote.themes.ThemeWrapType;
import com.mobile.kiril.tagnote.themes.Themes;
import com.mobile.kiril.tagnote.touchListeners.ThemePreviewTouchListener;
import com.transitionseverywhere.*;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.kiril.tagnote.toastEnum.ToastErrors;
import com.mobile.kiril.tagnote.toastEnum.ToastWarnings;
import com.mobile.kiril.tagnote.entities.Category;
import com.mobile.kiril.tagnote.entities.Note;
import com.mobile.kiril.tagnote.entities.Tag;
import com.mobile.kiril.tagnote.fordatabase.DBHelper;
import com.mobile.kiril.tagnote.presenters.MainPresenter;
import com.mobile.kiril.tagnote.textWatchers.SearchCategoryTextWatcher;
import com.mobile.kiril.tagnote.textWatchers.SearchTagTextWatcher;
import com.mobile.kiril.tagnote.touchListeners.MenuCategoryTouchListener;
import com.mobile.kiril.tagnote.touchListeners.MenuCloseTouchListener;
import com.mobile.kiril.tagnote.touchListeners.MenuNoteTouchListener;
import com.mobile.kiril.tagnote.touchListeners.MenuOpenTouchListener;
import com.mobile.kiril.tagnote.views.MainView;
import com.transitionseverywhere.extra.Scale;

import org.apmem.tools.layouts.FlowLayout;
import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.BillingRequests;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.EmptyRequestListener;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;
import org.solovyev.android.checkout.Sku;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity implements MainView, ThemePreviewMovement, View.OnClickListener {
    private final Context context = this;
    private MainPresenter mainPresenter;
    private DBHelper dbHelper;
    private Button addNoteBtn, changeBtn, menuChangeRemoveCategoryBtn, menuChangeRemoveCategoryNoteBtn, menuChangeRemoveCancleBtn, popupRemoveBtnYes, popupRemoveBtnNo,
            themeBuyCardBtn, offerPopupBtn, ratePopupBtnYes, ratePopupBtnNo, ratePopupBtnNever;
    private ImageButton menuBtn;
    private ImageView tagAngelDown, addCategoryIc, allNotesIc, changeTagAddIc, clearTagIc, changeCategoryAddIc, clearCategoryIc, popupRemoveIc, menuChangeRemoveIc,
            menuTagHideZone1, menuTagHideZone2, changeModTagHideZone1, changeModTagHideZone2, changeModCategoryHideZone1, changeModCategoryHideZone2,
            menuChangeBarIc1, menuChangeBarIc2, menuChangeBarIc3, themeBackIc, themeDefaultHideZone1, themeDefaultHideZone2, themeDefaultTitleIc, themeListTopHideZone,
            themeBuyBtnIc, settingsBackBtnIc, purchaseNotificationIc, themePurchasedTitleIc, themePurchasedHideZone1, themePurchasedHideZone2, menuSettingsIcBtn, themeBtnHideZone,
            offerPopupCloseIc, offerPopupIc, themeBuyCardSaleIc, themeToplineSaleIc, offerPopupRibbon, giftPopupCloseIc, settingsMaybeLaterIc;
    private LinearLayout containerNote, containerCategory, menuCloseZone, allNoteCategory, barContainer, closeChangeModZone,
            changeModContainer, changeModCategory, changeModCategoryList, changeModClearCategory, changeModTag, changeModSpot, menuSpotList,
            changeModSpotCurrentContainer, menuBarChangeContainer, menuChangeMix, menuChangeSpot, popupNotificationContainer,
            menuChangeMixEtContainer, menuChangeMixList, menuChangeSelectedCategoryContainer, menuChangeRemove, categoryEtContainer, tagEtContainer,
            menuSwipeContainer, menuMain, changeModClearTag, changeModTagExistList, tagBtn, changeModSpotIcContainer,
            changeModCategoryEditBox, changeModTagEditBox, changeModAnimContainer, addNoteBtnContainer, menuMainView, tagAngelDownBox,
            allNotesBorderTop, allNotesBorderBottom, popupRemoveBlurView, popupRemoveBox, popupRemoveBorderTop, changeModBarBorder1, changeModBarBorder2, changeModBarBorder3,
            menuChangeBarBorder1, menuChangeBarBorder2, menuHideBlurZone, menuChangeRemoveBox, menuChangeRemoveBorder, menuChangeMixBox, mainTopline, themeMainContainer,
            themeTopline, themeList, themeDefaultTitleLine, themeDefaultContainer, themeDefaultDropTitle, themePreviewBlur, themePreviewScreen,
            themePreview1, themePreview2, themePreview3, themePreviewNav1, themePreviewNav2, themePreviewNav3, themePreviewLeftTap, themePreviewRightTap, themeBuyBtn, themeBuyCardBlur,
            themeBuyCardContainer, themeBuyCardContainerTopline, themeBuyCardMiniatureContainer, themeCurrentContainer, settingsScreen, themeCollectionsList, settingsTopline,
            purchaseNotificationBlur, purchaseNotificationContainer, themePurchasedTitleLine, themePurchasedContainer, themePurchasedDropTitle, offerPopupBlur, offerPopupContainer,
            themeBuyCardSaleRedLine, offerPopupBox, offerPopupUnderline, giftPopupBlur, giftPopupContainer, giftPopupTextBox, giftPopupMiniatureContainer, menuOfferTimerBox, ratePopupBlur,
            ratePopupContainer;
    private RelativeLayout menuBarSettings, barTagBtn, barCategoryBtn, barSpotBtn, barRemoveBtn, menuLayout, menuChangeMixBtn, menuChangeSpotBtn, menuChangeRemoveBtn, mainContainer,
            menuScrollTagBox, changeCategoryAddBtn, changeTagAddBtn, addCategoryBtn, changeModView, changeModSpotViewContainer, changeModCurrentSpotViewContainer,
            clearTagAcBox, clearCategoryAcBox, menuChangeHideContainer, themeBtn, themeBackBtn, themePreviewMainContainer, themePreviewContainerParent,
            themePreviewUnderlineTop, themePreviewUnderlineBottom, themePreviewContainer, themeBuyBtnIcBox, themeBuyCardScreen, themeBuyCardPriceBox, settingsBackBtn,
            purchaseNotificationAcBox, themeBuyCardSalePriceBox, offerPopupIcBox1, offerPopupIcBox2, giftPopupBox, giftPopupCloseBox, menuIcOfferGiftBox;
    private EditText categoryNameEt, changeCategoryEt, changeTagEt, menuChangeNewCategoryNameEt, tagNameEt;
    private TextView titleNote, allNotesAmount, changeMenuBtn, menuChangeNewCategoryAddBtn, tagBtnTextView, allNotesTextView, clearTagTextView,
            clearCategoryTextView, popupRemoveTitle, menuChangeMixTitle, menuChangeRemoveTitle, themeBtnText, themeToplineTitle, themeDefaultTitle, themeBuyBtnText,
            themeBuyCardThemeTitle, themeBuyCardPrice, settingsToplineTitle, purchaseNotificationText, themePurchasedTitle, offerPopupText1, offerPopupText2, themeBuyCardSalePrice,
            menuOfferTimer, settingsMaybeLaterText, ratePopupText;
    private Intent noteIntent;
    private String currentCategory, testColor, SECOND_LOG, LOG;
    private FlowLayout containerTag, changeModTagList;
    private boolean tagBtnSwither, isChangeModOn, isChangeContainerShow, isMenuChangeModOn, isHideMenuShow, isPopupOpen, isOpenAnim, isPopupAnim, isMenuOpen, isThemeScreenOpen,
            isSettingsOpen, isThemeBuyCardOpen, isThemePreviewOpen, isBuyCardTheme;
    private Map<Integer, Note> tempNoteMap, tempSelectedMap;
    private Map<Integer, Category> tempCategoryMap, tempSelectedCategoryMap;
    private Map<Integer, Theme> tempThemeMap;
    private Map<String, Integer> mapColor;
    private Map<String, Theme> themes;
    private Map<String, Collection> collections;
    private Map<String, LinearLayout> tempCollectionsLines;
    private Map<Integer, Collection> tempCollectionMap;
    private Map<String, Boolean> collectionsPurchasesMap;
    private Map<String, String[]> tempProductsPriceMap;
    private List<String> spotIcList;
    private int screenXSize, screenYSize, menuXSize, cacheMenuMod, cacheChangeMod, tempCacheChangeMod, themePreviewXSize, themeBuyBtnRightMargin, themeBuyBtnBottomMargin,
            tempCollectionId;
    private float screenXSizeFloat, screenYSizeFloat, changeMenuYSizeFloat, changeCloseYSizeFloat, menuXSizeF;
    private RelativeLayout.LayoutParams menuParams, settingsScreenParams;
    private ScrollView menuScrollTag, menuChangeMixScrollView;
    private List<View> changeModContainersList, changeModBtnsList, changeModMenuContainersList, changeModMenuBtnsList, themePreviewNavItemsList, themePreviewScreenMatrix;
    private SpotRowBuilderMain spotRowBuilderMain;
    private SpotRowBuilderMenu spotRowBuilderMenu;
    private List<ThemeViewWrap> themeWrapsList;
    private SharedPreferences themePref;
    private int[] themePreviewNavMatrix;
    private Theme tempTheme;
    private Collection tempCollection;
    private ActivityCheckout mCheckout;
    private Inventory mInventory;
    private Collections themesCollections;
    private GeneralAttention generalAttention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCheckout = Checkout.forActivity(this, Tagnote.get().getBilling());
        //call & check isBilling
        mCheckout.start();
        //infinite check purchase, with listener
        mCheckout.createPurchaseFlow(new PurchaseListener());
        //get data about product & load in callback
        mInventory = mCheckout.makeInventory();

        //hideZones
        menuTagHideZone1 = findViewById(R.id.menuTagHideZone1);
        menuTagHideZone2 = findViewById(R.id.menuTagHideZone2);
        changeModTagHideZone1 = findViewById(R.id.changeModTagHideZone1);
        changeModTagHideZone2 = findViewById(R.id.changeModTagHideZone2);
        changeModCategoryHideZone1 = findViewById(R.id.changeModCategoryHideZone1);
        changeModCategoryHideZone2 = findViewById(R.id.changeModCategoryHideZone2);

        //tempMap
        tempNoteMap = new HashMap<>();
        tempSelectedMap = new HashMap<>();
        tempCategoryMap = new HashMap<>();
        tempSelectedCategoryMap = new HashMap<>();
        tempThemeMap = new HashMap<>();
        tempCollectionMap = new HashMap<>();
        tempCollectionsLines = new HashMap<>();
        tempProductsPriceMap = new HashMap<>();

        //debug
        tempCollection = new Collection("0", "Just collection", "Just collection", "0");

        //tech
        LOG = "log";
        SECOND_LOG = "secondLog";
        dbHelper = new DBHelper(this);
        mainPresenter = new MainPresenter(this, dbHelper);
        noteIntent = new Intent(this, NoteActivity.class);
        tagBtnSwither = false;
        Intent thisIntent = getIntent();
        changeModContainersList = new ArrayList<>();
        changeModBtnsList = new ArrayList<>();
        changeModMenuContainersList = new ArrayList<>();
        changeModMenuBtnsList = new ArrayList<>();
        mapColor = new HashMap<>();
        themes = new HashMap<>();
        collections = new HashMap<>();
        collectionsPurchasesMap = new HashMap<>();
        isOpenAnim = false;
        isPopupAnim = false;
        isMenuOpen = false;
        isThemeScreenOpen = false;
        isSettingsOpen = false;
        isThemeBuyCardOpen = false;
        isThemePreviewOpen = false;
        cacheMenuMod = -1;
        cacheChangeMod = -1;
        tempCacheChangeMod = -1;
        testColor = "#FF00F2";
        themePreviewNavItemsList = new ArrayList<>();
        themePreviewNavMatrix = new int[]{1,0,0};
        themePreviewScreenMatrix = new ArrayList<>();
        isBuyCardTheme = true;
        generalAttention = new GeneralAttention();

        currentCategory = getResources().getString(R.string.all_notes);

        try {
            String tempCurrentCategory = thisIntent.getStringExtra("cacheCategory");
            if(!tempCurrentCategory.equalsIgnoreCase(null)) currentCategory = tempCurrentCategory;
        } catch (NullPointerException e) {}

        //displaySize
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenXSize = size.x;
        screenYSize = size.y;
        float screenYSizeF = size.y;
        float screenXSizeF = size.x;
        screenXSizeFloat = size.x;
        screenYSizeFloat = size.y;
        changeMenuYSizeFloat = (screenYSizeFloat - dpToPixel(55))/100*75;
        changeCloseYSizeFloat = (screenYSizeFloat - dpToPixel(55))/100*25;
        menuXSize = size.x/100*85;
        menuXSizeF = size.x/100*85;
        spotRowBuilderMain = new SpotRowBuilderMain(screenYSizeF, screenXSizeF, getResources().getDisplayMetrics().density);
        spotRowBuilderMenu = new SpotRowBuilderMenu(screenYSizeF, menuXSizeF, getResources().getDisplayMetrics().density);

        //forMenu
        themeBtnText = findViewById(R.id.themeBtnText);
        themeBtn = (RelativeLayout) findViewById(R.id.themeBtn);
        themeBtn.setOnClickListener(this);
        allNotesBorderTop = (LinearLayout) findViewById(R.id.allNotesBorderTop);
        allNotesBorderBottom = (LinearLayout) findViewById(R.id.allNotesBorderBottom);
        allNotesIc = (ImageView) findViewById(R.id.allNotesIc);
        allNotesTextView = (TextView) findViewById(R.id.allNotesTextView);
        tagAngelDown = (ImageView) findViewById(R.id.tagAngelDown);
        tagAngelDownBox = (LinearLayout) findViewById(R.id.tagAngelDownBox);
        menuMainView = (LinearLayout) findViewById(R.id.menuMainView);
        menuLayout = (RelativeLayout) findViewById(R.id.menuLayout);
        menuBtn = (ImageButton) findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(this);
        menuCloseZone = (LinearLayout) findViewById(R.id.menuCloseZone);
        menuCloseZone.setOnClickListener(this);
        menuMain = (LinearLayout) findViewById(R.id.menuMain);
        menuSettingsIcBtn = findViewById(R.id.menuSettingsIcBtn);
        themeBtnHideZone = findViewById(R.id.themeBtnHideZone);
            //category
        addCategoryIc = (ImageView) findViewById(R.id.addCategoryIc);
        categoryNameEt = (EditText) findViewById(R.id.categoryNameEt);
        addCategoryBtn = (RelativeLayout) findViewById(R.id.addCategoryBtn);
        addCategoryBtn.setOnClickListener(this);
        allNoteCategory = (LinearLayout) findViewById(R.id.allNoteCategory);
        allNoteCategory.setOnClickListener(this);
        containerCategory = (LinearLayout) findViewById(R.id.containerCategory);
        categoryEtContainer = (LinearLayout) findViewById(R.id.categoryEtContainer);
                //categorySearch
        SearchCategoryTextWatcher searchCategoryWatcher = new SearchCategoryTextWatcher(dbHelper, this, containerCategory, categoryNameEt);
        categoryNameEt.addTextChangedListener(searchCategoryWatcher);
            //tags
        tagBtnTextView = (TextView) findViewById(R.id.tagBtnTextView);
        tagBtn = (LinearLayout) findViewById(R.id.tagBtn);
        tagBtn.setOnClickListener(this);
        containerTag = findViewById(R.id.containerTag);
        tagEtContainer = (LinearLayout) findViewById(R.id.tagEtContainer);
        menuScrollTagBox = (RelativeLayout) findViewById(R.id.menuScrollTagBox);
                //tagSearch
        tagNameEt = (EditText) findViewById(R.id.tagNameEt);
        SearchTagTextWatcher searchTagWatcher = new SearchTagTextWatcher(dbHelper, this, containerTag, tagNameEt);
        tagNameEt.addTextChangedListener(searchTagWatcher);
            //offerTimer
        menuOfferTimerBox = findViewById(R.id.menuOfferTimerBox);
        menuOfferTimer = findViewById(R.id.menuOfferTimer);
        menuIcOfferGiftBox = findViewById(R.id.menuIcOfferGiftBox);

        //mainScreen
        mainTopline = findViewById(R.id.mainTopline);
        mainContainer = (RelativeLayout) findViewById(R.id.mainContainer);
        containerNote = (LinearLayout) findViewById(R.id.containerNote);
        titleNote = (TextView) findViewById(R.id.titleNote);
        allNotesAmount = (TextView) findViewById(R.id.allNotesAmount);
        changeBtn = findViewById(R.id.changeBtn);
        changeBtn.setOnClickListener(this);
            //currentCategory
        setCurrentCategory(currentCategory);

        //changeMod
        changeModBarBorder1 = (LinearLayout) findViewById(R.id.changeModBarBorder1);
        changeModBarBorder2 = (LinearLayout) findViewById(R.id.changeModBarBorder2);
        changeModBarBorder3 = (LinearLayout) findViewById(R.id.changeModBarBorder3);
        addNoteBtnContainer = (LinearLayout) findViewById(R.id.addNoteBtnContainer);
        closeChangeModZone = (LinearLayout) findViewById(R.id.closeChangeModZone);
        closeChangeModZone.setOnClickListener(this);
        changeModAnimContainer = (LinearLayout) findViewById(R.id.changeModAnimContainer);
        changeModView = (RelativeLayout) findViewById(R.id.changeModView);
            //closeChangeSize
        int closeZoneHeight = screenYSize - ((int)dpToPixel(330 + 55)) + 1;
        if(screenYSize < screenXSize) closeZoneHeight = (screenYSize - (int)dpToPixel(55))/100*25 + 1;
        RelativeLayout.LayoutParams closeChangeModParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, closeZoneHeight);
        closeChangeModZone.setLayoutParams(closeChangeModParams);
        changeModContainer = (LinearLayout) findViewById(R.id.changeModContainer);
        changeModCategory = (LinearLayout) findViewById(R.id.changeModCategory);
            //changeScreenSize
        int changeScreenSize = (int)dpToPixel(330);
        if(screenYSize < screenXSize) changeScreenSize = (screenYSize - (int)dpToPixel(55))/100*75;
        LinearLayout.LayoutParams changeScreenParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, changeScreenSize);
        changeModContainer.setLayoutParams(changeScreenParams);

        RelativeLayout.LayoutParams animContainerParams = (RelativeLayout.LayoutParams) changeModAnimContainer.getLayoutParams();
        animContainerParams.bottomMargin = -(int)dpToPixel(330);
        changeModAnimContainer.setLayoutParams(animContainerParams);
            //
            //category
        clearCategoryAcBox = (RelativeLayout) findViewById(R.id.clearCategoryAcBox);
        clearCategoryIc = (ImageView) findViewById(R.id.clearCategoryIc);
        clearCategoryTextView = (TextView) findViewById(R.id.clearCategoryTextView);
        changeCategoryAddIc = (ImageView) findViewById(R.id.changeCategoryAddIc);
        changeModCategoryEditBox = (LinearLayout) findViewById(R.id.changeModCategoryEditBox);
        changeModCategoryList = (LinearLayout) findViewById(R.id.changeModCategoryList);
        changeCategoryAddBtn = (RelativeLayout) findViewById(R.id.changeCategoryAddBtn);
        changeCategoryAddBtn.setOnClickListener(this);
        changeModClearCategory = (LinearLayout) findViewById(R.id.changeModClearCategory);
        changeModClearCategory.setOnClickListener(this);
                //categorySearch
        changeCategoryEt = (EditText) findViewById(R.id.changeCategoryEt);
        SearchCategoryTextWatcher changeCategorySearch = new SearchCategoryTextWatcher(dbHelper, this, changeModCategoryList, changeCategoryEt);
        changeCategoryEt.addTextChangedListener(changeCategorySearch);
            //tags
        clearTagTextView = (TextView) findViewById(R.id.clearTagTextView);
        clearTagAcBox = (RelativeLayout) findViewById(R.id.clearTagAcBox);
        clearTagIc = (ImageView) findViewById(R.id.clearTagIc);
        changeTagAddIc = (ImageView) findViewById(R.id.changeTagAddIc);
        changeModTagEditBox = (LinearLayout) findViewById(R.id.changeModTagEditBox);
        changeModTag = (LinearLayout) findViewById(R.id.changeModTag);
        changeTagAddBtn = (RelativeLayout) findViewById(R.id.changeTagAddBtn);
        changeTagAddBtn.setOnClickListener(this);
        changeModTagList = (FlowLayout) findViewById(R.id.changeModTagList);
        changeModTagExistList = (LinearLayout) findViewById(R.id.changeModTagExistList);
        changeModClearTag = (LinearLayout) findViewById(R.id.changeModClearTag);
        changeModClearTag.setOnClickListener(this);
                //tagSearch
        changeTagEt = (EditText) findViewById(R.id.changeTagEt);
        SearchTagTextWatcher changeTagSearch = new SearchTagTextWatcher(dbHelper, this, changeModTagList, changeTagEt);
        changeTagEt.addTextChangedListener(changeTagSearch);
            //spot
        changeModSpotViewContainer = (RelativeLayout) findViewById(R.id.changeModSpotViewContainer);
        changeModCurrentSpotViewContainer = (RelativeLayout) findViewById(R.id.changeModCurrentSpotViewContainer);
        changeModSpotIcContainer = (LinearLayout) findViewById(R.id.changeModSpotIcContainer);
        changeModSpot = (LinearLayout) findViewById(R.id.changeModSpot);
        changeModSpotCurrentContainer = (LinearLayout) findViewById(R.id.changeModSpotCurrentContainer);
        spotIcList = new SpotManager().getColorIcList();

        //menuChangeBar
        menuChangeBarBorder1 = (LinearLayout) findViewById(R.id.menuChangeBarBorder1);
        menuChangeBarBorder2 = (LinearLayout) findViewById(R.id.menuChangeBarBorder2);
        menuBarSettings = (RelativeLayout) findViewById(R.id.menuBarSettings);
        menuBarSettings.setOnClickListener(this);
        menuBarChangeContainer = (LinearLayout) findViewById(R.id.menuBarChangeContainer);
            //listener
        menuChangeMixBtn = (RelativeLayout) findViewById(R.id.menuChangeMixBtn);
        menuChangeMixBtn.setOnClickListener(this);
        menuChangeSpotBtn = (RelativeLayout) findViewById(R.id.menuChangeSpotBtn);
        menuChangeSpotBtn.setOnClickListener(this);
        menuChangeRemoveBtn = (RelativeLayout) findViewById(R.id.menuChangeRemoveBtn);
        menuChangeRemoveBtn.setOnClickListener(this);

        //menuChange
        menuHideBlurZone = (LinearLayout) findViewById(R.id.menuHideBlurZone);
        menuHideBlurZone.setOnClickListener(this);
        changeMenuBtn = (TextView) findViewById(R.id.changeMenuBtn);
        changeMenuBtn.setOnClickListener(this);
        menuChangeHideContainer = (RelativeLayout) findViewById(R.id.menuChangeHideContainer);
            //mix
        menuChangeBarIc1 = findViewById(R.id.menuChangeBarIc1);
        menuChangeMixBox = (LinearLayout) findViewById(R.id.menuChangeMixBox);
        menuChangeMixTitle = (TextView) findViewById(R.id.menuChangeMixTitle);
        menuChangeMix = (LinearLayout) findViewById(R.id.menuChangeMix);
        menuChangeMixEtContainer = (LinearLayout) findViewById(R.id.menuChangeMixEtContainer);
        menuChangeMixList = (LinearLayout) findViewById(R.id.menuChangeMixList);
        menuChangeSelectedCategoryContainer = (LinearLayout) findViewById(R.id.menuChangeSelectedCategoryContainer);
        menuChangeNewCategoryNameEt = (EditText) findViewById(R.id.menuChangeNewCategoryNameEt);
        menuChangeNewCategoryAddBtn = (TextView) findViewById(R.id.menuChangeNewCategoryAddBtn);
        menuChangeNewCategoryAddBtn.setOnClickListener(this);
        menuChangeMixScrollView = (ScrollView) findViewById(R.id.menuChangeMixScrollView);
            //spot
        menuChangeBarIc2 = findViewById(R.id.menuChangeBarIc2);
        menuChangeSpot = (LinearLayout) findViewById(R.id.menuChangeSpot);
        menuSpotList = (LinearLayout) findViewById(R.id.menuSpotList);
        LinearLayout.LayoutParams menuSpotListParams = new LinearLayout.LayoutParams(screenXSize/100*73, (int)dpToPixel(230));
        if(screenYSize < screenXSize) menuSpotListParams = new LinearLayout.LayoutParams(screenXSize/100*73, (int)dpToPixel(130));
        menuSpotListParams.gravity = Gravity.CENTER;
        menuSpotListParams.leftMargin = screenXSize/100*6;
        menuSpotList.setLayoutParams(menuSpotListParams);
            //remove
        menuChangeBarIc3 = findViewById(R.id.menuChangeBarIc3);
        menuChangeRemoveTitle = (TextView) findViewById(R.id.menuChangeRemoveTitle);
        menuChangeRemoveIc = (ImageView) findViewById(R.id.menuChangeRemoveIc);
        menuChangeRemoveBorder = (LinearLayout) findViewById(R.id.menuChangeRemoveBorder);
        menuChangeRemoveBox = (LinearLayout) findViewById(R.id.menuChangeRemoveBox);
        menuChangeRemove = (LinearLayout) findViewById(R.id.menuChangeRemove);
        menuChangeRemoveCategoryBtn = (Button) findViewById(R.id.menuChangeRemoveCategoryBtn);
        menuChangeRemoveCategoryBtn.setOnClickListener(this);
        menuChangeRemoveCategoryNoteBtn = (Button) findViewById(R.id.menuChangeRemoveCategoryNoteBtn);
        menuChangeRemoveCategoryNoteBtn.setOnClickListener(this);
        menuChangeRemoveCancleBtn = (Button) findViewById(R.id.menuChangeRemoveCancleBtn);
        menuChangeRemoveCancleBtn.setOnClickListener(this);

        //barBtn
            //tag
        barTagBtn = (RelativeLayout) findViewById(R.id.barTagBtn);
        barTagBtn.setOnClickListener(this);
            //category
        barCategoryBtn = (RelativeLayout) findViewById(R.id.barCategoryBtn);
        barCategoryBtn.setOnClickListener(this);
            //spot
        barSpotBtn = (RelativeLayout) findViewById(R.id.barSpotBtn);
        barSpotBtn.setOnClickListener(this);
            //remove
        barRemoveBtn = (RelativeLayout) findViewById(R.id.barRemoveBtn);
        barRemoveBtn.setOnClickListener(this);

        //downBar
        addNoteBtn = (Button) findViewById(R.id.addNoteBtn);
        addNoteBtn.setOnClickListener(this);
        barContainer = findViewById(R.id.barContainer);

        LinearLayout.LayoutParams addNoteBtnContainerParams = (LinearLayout.LayoutParams) addNoteBtnContainer.getLayoutParams();
        addNoteBtnContainerParams.topMargin = (int)-(dpToPixel(55));
        addNoteBtnContainer.setLayoutParams(addNoteBtnContainerParams);

        //popUp
        isPopupOpen = false;
        popupRemoveBorderTop = (LinearLayout) findViewById(R.id.popupRemoveBorderTop);
        popupRemoveIc = (ImageView) findViewById(R.id.popupRemoveIc);
        popupRemoveTitle = (TextView) findViewById(R.id.popupRemoveTitle);
        popupRemoveBox = (LinearLayout) findViewById(R.id.popupRemoveBox);
        popupRemoveBlurView = (LinearLayout) findViewById(R.id.popupRemoveBlurView);
        popupNotificationContainer = findViewById(R.id.popupNotificationContainer);
        popupNotificationContainer.setOnClickListener(this);
        popupRemoveBtnYes = (Button) findViewById(R.id.popupRemoveBtnYes);
        popupRemoveBtnYes.setOnClickListener(this);
        popupRemoveBtnNo = (Button) findViewById(R.id.popupRemoveBtnNo);
        popupRemoveBtnNo.setOnClickListener(this);

        //menuMainSize
        menuParams = new RelativeLayout.LayoutParams(menuXSize, RelativeLayout.LayoutParams.MATCH_PARENT);
        menuParams.leftMargin = -menuXSize;
        menuMain.setLayoutParams(menuParams);

        //openTouchListeners
        menuSwipeContainer = (LinearLayout)findViewById(R.id.menuSwipeContainer);
        menuSwipeContainer.setOnTouchListener(new MenuOpenTouchListener(this, this, screenXSize, screenYSize, menuXSize));

        //closeTouchListeners
        ScrollView menuMainList = (ScrollView) findViewById(R.id.menuMainList);
        menuMainList.setOnTouchListener(new MenuCloseTouchListener(this, this));

        //themeScreen
            //main
        themeMainContainer = findViewById(R.id.themeMainContainer);
        RelativeLayout.LayoutParams themeContainerParams = new RelativeLayout.LayoutParams(menuXSize, RelativeLayout.LayoutParams.MATCH_PARENT);
        themeMainContainer.setLayoutParams(themeContainerParams);
        themeBackBtn = findViewById(R.id.themeBackBtn);
        themeBackBtn.setOnClickListener(this);
        themeDefaultDropTitle = findViewById(R.id.themeDefaultDropTitle);
        themeDefaultDropTitle.setOnClickListener(this);
        themeTopline = findViewById(R.id.themeTopline);
        themeBackIc = findViewById(R.id.themeBackIc);
        themeToplineTitle = findViewById(R.id.themeToplineTitle);
        themeCurrentContainer = findViewById(R.id.themeCurrentContainer);
        themeList = findViewById(R.id.themeList);
        themeDefaultTitleIc = findViewById(R.id.themeDefaultTitleIc);
        themeDefaultTitle = findViewById(R.id.themeDefaultTitle);
        themeDefaultTitleLine = findViewById(R.id.themeDefaultTitleLine);
        themeDefaultHideZone1 = findViewById(R.id.themeDefaultHideZone1);
        themeDefaultHideZone2 = findViewById(R.id.themeDefaultHideZone2);
        themeDefaultContainer = findViewById(R.id.themeDefaultContainer);
        themeListTopHideZone = findViewById(R.id.themeListTopHideZone);
        themeCollectionsList = findViewById(R.id.themeCollectionsList);
        themePurchasedContainer = findViewById(R.id.themePurchasedContainer);
        themePurchasedDropTitle = findViewById(R.id.themePurchasedDropTitle);
        themePurchasedDropTitle.setOnClickListener(this);
        themeToplineSaleIc = findViewById(R.id.themeToplineSaleIc);

        themePurchasedTitleIc = findViewById(R.id.themePurchasedTitleIc);
        themePurchasedTitle = findViewById(R.id.themePurchasedTitle);
        themePurchasedTitleLine = findViewById(R.id.themePurchasedTitleLine);
        themePurchasedHideZone1 = findViewById(R.id.themePurchasedHideZone1);
        themePurchasedHideZone2 = findViewById(R.id.themePurchasedHideZone2);
            //preview
        themePreviewContainer = findViewById(R.id.themePreviewContainer);
        themePreviewXSize = screenXSize/100*80;
        themePreviewBlur = findViewById(R.id.themePreviewBlur);
        themePreviewBlur.setOnClickListener(this);
        themePreviewLeftTap = findViewById(R.id.themePreviewLeftTap);
        themePreviewLeftTap.setOnClickListener(this);
        themePreviewRightTap = findViewById(R.id.themePreviewRightTap);
        themePreviewRightTap.setOnClickListener(this);
        themePreviewMainContainer = findViewById(R.id.themePreviewMainContainer);
        themePreviewNav1 = findViewById(R.id.themePreviewNav1);
        themePreviewNav2 = findViewById(R.id.themePreviewNav2);
        themePreviewNav3 = findViewById(R.id.themePreviewNav3);
        themePreviewUnderlineTop = findViewById(R.id.themePreviewUnderlineTop);
        themePreviewUnderlineBottom = findViewById(R.id.themePreviewUnderlineBottom);
            //buyBtn
        themeBuyBtn = findViewById(R.id.themeBuyBtn);
        themeBuyBtn.setOnClickListener(this);
        themeBuyBtnIcBox = findViewById(R.id.themeBuyBtnIcBox);
        themeBuyBtnIc = findViewById(R.id.themeBuyBtnIc);
        themeBuyBtnText = findViewById(R.id.themeBuyBtnText);
            //BuyCard
        themeBuyCardScreen = findViewById(R.id.themeBuyCardScreen);
        themeBuyCardBlur = findViewById(R.id.themeBuyCardBlur);
        themeBuyCardBlur.setOnClickListener(this);
        themeBuyCardContainer = findViewById(R.id.themeBuyCardContainer);
        themeBuyCardPriceBox = findViewById(R.id.themeBuyCardPriceBox);
        themeBuyCardContainerTopline = findViewById(R.id.themeBuyCardContainerTopline);
        themeBuyCardThemeTitle = findViewById(R.id.themeBuyCardThemeTitle);
        themeBuyCardPrice = findViewById(R.id.themeBuyCardPrice);
        themeBuyCardBtn = findViewById(R.id.themeBuyCardBtn);
        themeBuyCardBtn.setOnClickListener(this);
        themeBuyCardMiniatureContainer = findViewById(R.id.themeBuyCardMiniatureContainer);
        themeBuyCardSalePriceBox = findViewById(R.id.themeBuyCardSalePriceBox);
        themeBuyCardSalePrice = findViewById(R.id.themeBuyCardSalePrice);
        themeBuyCardSaleRedLine = findViewById(R.id.themeBuyCardSaleRedLine);
        themeBuyCardSaleIc = findViewById(R.id.themeBuyCardSaleIc);
                //previewScreen Size Correct
        themePreviewScreen = findViewById(R.id.themePreviewScreen);
        RelativeLayout.LayoutParams previewScreenParams = (RelativeLayout.LayoutParams) themePreviewScreen.getLayoutParams();
        previewScreenParams.height = screenYSize/100*80 + (int)dpToPixel(48);
        previewScreenParams.width = themePreviewXSize;
        themePreviewScreen.setLayoutParams(previewScreenParams);
        themePreviewContainerParent = findViewById(R.id.themePreviewContainerParent);
        LinearLayout.LayoutParams previewContainerParams = (LinearLayout.LayoutParams) themePreviewContainerParent.getLayoutParams();
        previewContainerParams.height = screenYSize/100*80;
        previewContainerParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        themePreviewContainerParent.setLayoutParams(previewContainerParams);
        themePreview1 = findViewById(R.id.themePreview1);
        themePreview2 = findViewById(R.id.themePreview2);
        themePreview3 = findViewById(R.id.themePreview3);
        RelativeLayout.LayoutParams themePreviewParams = (RelativeLayout.LayoutParams) themePreview1.getLayoutParams();
        themePreviewParams.width = themePreviewXSize;
        themePreview1.setLayoutParams(themePreviewParams);
        themePreview2.setLayoutParams(themePreviewParams);
        themePreview3.setLayoutParams(themePreviewParams);
        themePreviewScreenMatrix.add(themePreview1);
        themePreviewScreenMatrix.add(themePreview2);
        themePreviewScreenMatrix.add(themePreview3);
        View.OnTouchListener themePreviewMoveTouchListener = new ThemePreviewTouchListener(this, this);
        themePreviewContainer.setOnTouchListener(themePreviewMoveTouchListener);

        themePreviewNavItemsList.add(themePreviewNav1);
        themePreviewNavItemsList.add(themePreviewNav2);
        themePreviewNavItemsList.add(themePreviewNav3);

        //purchaseNotification
        purchaseNotificationBlur = findViewById(R.id.purchaseNotificationBlur);
        purchaseNotificationBlur.setOnClickListener(this);
        purchaseNotificationContainer = findViewById(R.id.purchaseNotificationContainer);
        purchaseNotificationAcBox = findViewById(R.id.purchaseNotificationAcBox);
        purchaseNotificationIc = findViewById(R.id.purchaseNotificationIc);
        purchaseNotificationText = findViewById(R.id.purchaseNotificationText);

        //settingsScreen
        settingsScreen = findViewById(R.id.settingsScreen);
        settingsTopline = findViewById(R.id.settingsTopline);
        settingsBackBtnIc = findViewById(R.id.settingsBackBtnIc);
        settingsToplineTitle = findViewById(R.id.settingsToplineTitle);
        settingsBackBtn = findViewById(R.id.settingsBackBtn);
        settingsBackBtn.setOnClickListener(this);
        settingsMaybeLaterIc = findViewById(R.id.settingsMaybeLaterIc);
        settingsMaybeLaterText = findViewById(R.id.settingsMaybeLaterText);

        settingsScreenParams = new RelativeLayout.LayoutParams(menuXSize, screenYSize);
        settingsScreenParams.leftMargin = -menuXSize;
        settingsScreen.setLayoutParams(settingsScreenParams);

        //offerPopup
        offerPopupContainer = findViewById(R.id.offerPopupContainer);
        offerPopupBlur = findViewById(R.id.offerPopupBlur);
        offerPopupCloseIc = findViewById(R.id.offerPopupCloseIc);
        offerPopupCloseIc.setOnClickListener(this);
        offerPopupText1 = findViewById(R.id.offerPopupText1);
        offerPopupText2 = findViewById(R.id.offerPopupText2);
        offerPopupIc = findViewById(R.id.offerPopupIc);
        offerPopupRibbon = findViewById(R.id.offerPopupRibbon);
        offerPopupBox = findViewById(R.id.offerPopupBox);
        offerPopupUnderline = findViewById(R.id.offerPopupUnderline);
        offerPopupIcBox2 = findViewById(R.id.offerPopupIcBox2);
        offerPopupIcBox1 = findViewById(R.id.offerPopupIcBox1);
        offerPopupBtn = findViewById(R.id.offerPopupBtn);

        //giftPopup
        giftPopupBlur = findViewById(R.id.giftPopupBlur);
        giftPopupContainer = findViewById(R.id.giftPopupContainer);
        giftPopupTextBox = findViewById(R.id.giftPopupTextBox);
        giftPopupBox = findViewById(R.id.giftPopupBox);
        giftPopupMiniatureContainer = findViewById(R.id.giftPopupMiniatureContainer);
        giftPopupCloseBox = findViewById(R.id.giftPopupCloseBox);
        giftPopupCloseIc = findViewById(R.id.giftPopupCloseIc);

        //ratePopup
        ratePopupBlur = findViewById(R.id.ratePopupBlur);
        ratePopupContainer = findViewById(R.id.ratePopupContainer);
        ratePopupText = findViewById(R.id.ratePopupText);
        ratePopupBtnYes = findViewById(R.id.ratePopupBtnYes);
        ratePopupBtnNo = findViewById(R.id.ratePopupBtnNo);
        ratePopupBtnNever = findViewById(R.id.ratePopupBtnNever);

        //sizeCorrect
        if(screenYSize > screenXSize) {
            //menu
            menuChangeMixScrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(310)));

            //changeMod
            FrameLayout.LayoutParams tagExistListParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(40));
            tagExistListParams.topMargin = (int)dpToPixel(6);
            changeModTagExistList.setLayoutParams(tagExistListParams);

            LinearLayout.LayoutParams categoryEditBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(60));
            changeModCategoryEditBox.setLayoutParams(categoryEditBoxParams);

            LinearLayout.LayoutParams tagEditBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(60));
            changeModTagEditBox.setLayoutParams(tagEditBoxParams);

            LinearLayout.LayoutParams changeContainerParams = (LinearLayout.LayoutParams) changeModContainer.getLayoutParams();
            changeContainerParams.height = (int) changeMenuYSizeFloat;
            changeModContainer.setLayoutParams(changeContainerParams);

            RelativeLayout.LayoutParams changeCloseParams = (RelativeLayout.LayoutParams) closeChangeModZone.getLayoutParams();
            changeCloseParams.height = (int)changeCloseYSizeFloat;
            closeChangeModZone.setLayoutParams(changeCloseParams);

            RelativeLayout.LayoutParams changeAnimContainerParams = (RelativeLayout.LayoutParams) changeModAnimContainer.getLayoutParams();
            changeAnimContainerParams.bottomMargin = (int)(-changeMenuYSizeFloat);
            changeModAnimContainer.setLayoutParams(changeAnimContainerParams);
        } else {
            menuChangeMixScrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(124)));

            FrameLayout.LayoutParams tagExistListParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(40));
            tagExistListParams.topMargin = (int)dpToPixel(6);
            changeModTagExistList.setLayoutParams(tagExistListParams);

            LinearLayout.LayoutParams categoryEditBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(50));
            changeModCategoryEditBox.setLayoutParams(categoryEditBoxParams);

            LinearLayout.LayoutParams tagEditBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(50));
            changeModTagEditBox.setLayoutParams(tagEditBoxParams);

            LinearLayout.LayoutParams changeModCategoryEtParams = new LinearLayout.LayoutParams(screenXSize/100*80, LinearLayout.LayoutParams.MATCH_PARENT);
            changeCategoryEt.setLayoutParams(changeModCategoryEtParams);

            themePreviewXSize = screenXSize/100*40;
            previewScreenParams.width = themePreviewXSize;
            themePreviewParams.width = themePreviewXSize;

            //after anim
            RelativeLayout.LayoutParams changeAnimContainer = (RelativeLayout.LayoutParams) changeModAnimContainer.getLayoutParams();
            changeAnimContainer.bottomMargin = -(screenYSize - (int)dpToPixel(55))/100*75;
            changeModAnimContainer.setLayoutParams(changeAnimContainer);
        }

        themeBuyBtnRightMargin = themePreviewXSize/100*15;
        themeBuyBtnBottomMargin = (screenYSize/100*80)/100*30;

        RelativeLayout.LayoutParams themeBuyBtnParams = (RelativeLayout.LayoutParams) themeBuyBtn.getLayoutParams();
        themeBuyBtnParams.rightMargin = themeBuyBtnRightMargin;
        themeBuyBtnParams.bottomMargin = themeBuyBtnBottomMargin;
        themeBuyBtn.setLayoutParams(themeBuyBtnParams);

        //spotSizeCorrect
        RelativeLayout.LayoutParams spotBoxParams = new RelativeLayout.LayoutParams((int)spotRowBuilderMain.getBoxWidth(), (int)spotRowBuilderMain.getBoxHeight());
        spotBoxParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        spotBoxParams.addRule(RelativeLayout.CENTER_VERTICAL);
        int spotBoxMargin = (int)spotRowBuilderMain.getSpotBoxMargin();
        spotBoxParams.setMargins(spotBoxMargin, 0, spotBoxMargin, 0);
        int spotBoxPadding = (int)spotRowBuilderMain.getSpotBoxPadding();
        changeModSpotIcContainer.setLayoutParams(spotBoxParams);
        changeModSpotIcContainer.setPadding(spotBoxPadding, spotBoxPadding, spotBoxPadding, spotBoxPadding);

        LinearLayout.LayoutParams spotViewContainerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)changeMenuYSizeFloat/100*80);
        changeModSpotViewContainer.setLayoutParams(spotViewContainerParams);
        LinearLayout.LayoutParams currentSpotViewContainerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)changeMenuYSizeFloat/100*20);
        changeModCurrentSpotViewContainer.setLayoutParams(currentSpotViewContainerParams);

        //menuSpotSizeCorrect
        RelativeLayout.LayoutParams menuSpotBoxParams = new RelativeLayout.LayoutParams((int)spotRowBuilderMenu.getBoxWidth(), (int)spotRowBuilderMenu.getBoxHeight());
        menuSpotBoxParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        menuSpotBoxParams.addRule(RelativeLayout.CENTER_VERTICAL);
        int menuSpotBoxMargin = (int)spotRowBuilderMenu.getSpotBoxMargin();
        spotBoxParams.setMargins(menuSpotBoxMargin, 0, menuSpotBoxMargin, 0);
        int menuSpotBoxPadding = (int)spotRowBuilderMenu.getSpotBoxPadding();
        menuSpotList.setLayoutParams(menuSpotBoxParams);
        menuSpotList.setPadding(menuSpotBoxPadding, menuSpotBoxPadding, menuSpotBoxPadding, menuSpotBoxPadding);

        //hideKeyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //fillChangeModArrays
        changeModContainersList.add(changeModTag);
        changeModContainersList.add(changeModCategory);
        changeModContainersList.add(changeModSpot);
        changeModContainersList.add(popupNotificationContainer);

        changeModBtnsList.add(barTagBtn);
        changeModBtnsList.add(barCategoryBtn);
        changeModBtnsList.add(barSpotBtn);
        changeModBtnsList.add(barRemoveBtn);

        //fillMenuChangeModArrays
        changeModMenuContainersList.add(menuChangeMix);
        changeModMenuContainersList.add(menuChangeSpot);
        changeModMenuContainersList.add(menuChangeRemove);

        changeModMenuBtnsList.add(menuChangeMixBtn);
        changeModMenuBtnsList.add(menuChangeSpotBtn);
        changeModMenuBtnsList.add(menuChangeRemoveBtn);

        //theme
//        loadThemes();
        themesCollections = new Collections();
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
        ThemeSetter tempThemeSetter = new ThemeSetter(themeWrapsList, mapColor);
        buildTheme(tempThemeSetter);

        //methods
        updateNoteList();
        showCategory(mainPresenter.getAllCategory(), this, containerCategory);
        generalAttention.checkOffersAttention();
        if(generalAttention.checkRatePopup()) {
            if(generalAttention.checkRateYesClick()) ratePopupBtnNever.setVisibility(View.VISIBLE);
            switchRatePopup(true);
        }

//        mainPresenter.logAllNotes();
//        logMapColor();
    }

    @Override
    protected void onDestroy() {
        mCheckout.stop();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d(SECOND_LOG, "onActivityResult");
//        Log.d(SECOND_LOG, String.format("requestCode: %d, resultCode: %d", requestCode, resultCode));

        if(resultCode == -1) {
            generalAttention.productCheckout();
            showPurchaseNotification(true);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //menu
            case R.id.menuBtn :
                outFixMenu(0,0,true);

                break;
            case R.id.menuCloseZone :
                closeMenu();

                break;
            case R.id.addCategoryBtn :
                if(!isHideMenuShow) {
                    String name = categoryNameEt.getText().toString();

                    if(!name.equalsIgnoreCase("")) {
                        mainPresenter.addCategory(name, "", 0);
                        categoryNameEt.setText("");
                        refreshMenuCategoryList("all", null);
                    } else {
                        Log.d("log", "Name error in addCategory");
                    }
                }

                break;
            case R.id.allNoteCategory :
                if(!isHideMenuShow) {
                    showNotes(mainPresenter.getAllNotes(), this, containerNote);
                    setCurrentCategory(getResources().getString(R.string.all_notes));
                    closeMenu();
                }

                break;
            case R.id.tagBtn :
                switchMenuTagBox();

                break;
                //settings
            case R.id.menuBarSettings :
                switchSettingsScreen(true);
                break;
            case R.id.settingsBackBtn :
                switchSettingsScreen(false);
                break;
            //mainScreen
            case R.id.addNoteBtn :
                if(currentCategory.equalsIgnoreCase(getResources().getString(R.string.all_notes))) noteIntent.putExtra("category", "");
                else noteIntent.putExtra("category", currentCategory);

                noteIntent.putExtra("isExist", false);

                startActivity(noteIntent);

                break;
            case R.id.changeBtn :
                if(!isChangeModOn) {
                    changeBtn.setText(getResources().getString(R.string.cancel));

                    noteBtnAnim(true);

                    isChangeModOn = true;
                } else {
                    closeChangeMod();
                }

                break;
            //barChangeMod
            case R.id.barTagBtn :
                switchChangeModContainer(0);
                break;
            case R.id.barCategoryBtn :
                switchChangeModContainer(1);
                break;
            case R.id.barSpotBtn :
                switchChangeModContainer(2);
                break;
            case R.id.barRemoveBtn :
                switchChangeModContainer(3);
                break;
            //changeMod
            case R.id.changeModClearCategory :
                switchChangeModContainer(3);
                changeModRemoveCategory();
                break;
            case R.id.closeChangeModZone :
                if(isChangeModOn)
                    switchChangeModContainer(-1);
                break;
            case R.id.changeCategoryAddBtn :
                changeModUpdateCategory(changeCategoryEt.getText().toString());

                break;
            case R.id.changeTagAddBtn :
                changeModUpdateTag(changeTagEt.getText().toString());

                break;
            case R.id.changeModClearTag :
                clearTags();

                break;
            //menuChangeMod
            case R.id.changeMenuBtn :
                switchMenuChangeMod();
                break;
            case R.id.menuHideBlurZone :
                switchMenuChangeMod();
                break;
                //menuChangeBar
            case R.id.menuChangeSpotBtn :
                switchHideMenu(1);
                break;
            case R.id.menuChangeMixBtn :
                switchHideMenu(0);
                break;
            case R.id.menuChangeRemoveBtn :
                if(tempSelectedCategoryMap.size() > 0) switchHideMenu(2);
                break;
            case R.id.menuChangeNewCategoryAddBtn :
                mixCategory();
                break;
            case R.id.menuChangeRemoveCategoryBtn :
                removeCategory(false);
                break;
            case R.id.menuChangeRemoveCategoryNoteBtn :
                removeCategory(true);
                break;
            case R.id.menuChangeRemoveCancleBtn :
                closeHideMenu();
                break;
            //popup
            case R.id.popupNotificationContainer :
                if(isPopupOpen) switchPopupContainer();
                break;
            case R.id.popupRemoveBtnYes :
                if(isPopupOpen) switchPopupContainer();
                removeNoteEvent();
                break;
            case R.id.popupRemoveBtnNo :
                if(isPopupOpen) switchPopupContainer();
                break;
            //theme
            case R.id.themeBtn :
                switchThemeScreen(true);
                break;
            case R.id.themeBackBtn :
                switchThemeScreen(true);
                break;
            case R.id.themeDefaultDropTitle :
                dropThemeList(v);
                break;
            case R.id.themePurchasedDropTitle :
                dropThemeList(v);
                break;
            case R.id.themePreviewBlur :
                if(isThemePreviewOpen) closeThemePreview();
                break;
            case R.id.themePreviewLeftTap :
                moveThemePreview(false);
                break;
            case R.id.themePreviewRightTap :
                moveThemePreview(true);
                break;
            case R.id.themeBuyBtn :
                clickToBuyBtn();
                break;
            case R.id.themeBuyCardBlur :
                if(isThemeBuyCardOpen) switchThemeBuyCard(true);
                break;
            //themeNotification
            case R.id.purchaseNotificationBlur :
                Animation opacityOutAnim = AnimationUtils.loadAnimation(context, R.anim.opacity_out_anim_long);
                Animation dropOutAnimation = AnimationUtils.loadAnimation(context, R.anim.popup_hide_anim_long);

                purchaseNotificationBlur.setAnimation(opacityOutAnim);
                purchaseNotificationContainer.setAnimation(dropOutAnimation);

                purchaseNotificationBlur.setVisibility(View.GONE);
                purchaseNotificationContainer.setVisibility(View.GONE);
                break;
            case R.id.themeBuyCardBtn :
                checkoutWhenReady();
                break;
            //offerPopup
            case R.id.offerPopupCloseIc :
                switchOfferPopup(false);
                break;
        }
    }

    @Override
    public void moveMenu(int x, int y) {
        if(menuCloseZone.getVisibility() != View.VISIBLE) {
            menuCloseZone.setVisibility(View.VISIBLE);
        } else {
            float fX, fScreen, f;
            fX = x;
            fScreen = menuXSize;
            f = fX / fScreen;
            menuCloseZone.setAlpha(f + 0.2f);
        }
        menuParams.leftMargin = -menuXSize+x;
        menuMain.setLayoutParams(menuParams);
    }

    @Override
    public void openMenu() {
        menuLiveText(generalAttention.rankAttention());

        isMenuOpen = true;
        menuLayout.setVisibility(View.VISIBLE);
        menuParams.leftMargin = -menuXSize+(screenXSize/100*2);
        menuMain.setLayoutParams(menuParams);
    }

    @Override
    public void outFixMenu(int x, int y, boolean fixed) {
        if(x > screenXSize/100*45 || fixed) {
            startIcAttention();

            TransitionManager.beginDelayedTransition(menuMain, new ChangeBounds()
                    .setDuration(200)
                    .setInterpolator(new AccelerateDecelerateInterpolator()));

            TransitionManager.beginDelayedTransition(menuCloseZone);
            menuCloseZone.setVisibility(View.VISIBLE);
            menuCloseZone.setAlpha(1);

            allNotesAmount.setText(Integer.toString(countNotes()));
            if(isChangeModOn) closeChangeMod();
            menuParams.leftMargin = 0;
            menuMain.setLayoutParams(menuParams);
        } else {
            closeMenu();
        }
    }

    @Override
    public void closeMenu() {
        Log.d(SECOND_LOG, "closeMenu");

        isMenuOpen = false;

        if(isThemeScreenOpen) switchThemeScreen(true);

        closeCategoryChangeMod();

        themeBtnText.clearAnimation();

        TransitionManager.beginDelayedTransition(menuMain, new ChangeBounds()
                .setDuration(200)
                .setInterpolator(new AccelerateDecelerateInterpolator()));

        TransitionManager.beginDelayedTransition(menuCloseZone);
        menuCloseZone.setAlpha(0);

        closeHideMenu();
        menuParams.leftMargin = -menuXSize;
        menuMain.setLayoutParams(menuParams);
        menuCloseZone.setVisibility(View.GONE);

        refreshTheme();

        categoryNameEt.setText("");
        tagNameEt.setText("");

        if(isSettingsOpen) switchSettingsScreen(false);
    }

    private void menuLiveText(String text) {
        themeBtnText.setText(text);
        int minSymbol = 22;

        if(text.length() > minSymbol) {
            Animation liveAnim;

            if(text.length() <= 40) {
                liveAnim = AnimationUtils.loadAnimation(context, R.anim.live_text_short_anim);
            } else if(text.length() <= 70) {
                liveAnim = AnimationUtils.loadAnimation(context, R.anim.live_text_middle_anim);
                liveAnim.setDuration(10000);
            } else {
                liveAnim = AnimationUtils.loadAnimation(context, R.anim.live_text_long_anim);
                liveAnim.setDuration(15000);
            }

            themeBtnText.startAnimation(liveAnim);
        }
    }

    @Override
    public void categoryClick(View v) {
        if(!isHideMenuShow) {
            if(!isMenuChangeModOn) {
                TextView categoryView = v.findViewWithTag("categoryName");

                String category = categoryView.getText().toString();

                showNotes(mainPresenter.getNoteByCategory(category), v.getContext(), containerNote);

                setCurrentCategory(category);

                closeMenu();
            } else {
                LinearLayout selectIc = v.findViewWithTag("selectIc");
                int selectId = v.getId();

                if(selectIc.getVisibility() == View.GONE) {
                    tempSelectedCategoryMap.put(selectId, tempCategoryMap.get(selectId));
                    switchCategoryIcSelect(selectIc, true);
                } else {
                    tempSelectedCategoryMap.remove(selectId);
                    switchCategoryIcSelect(selectIc, false);
                }
            }
        }
    }

    @Override
    public void categoryLongPress(View v) {
        if(!isHideMenuShow){
            if(!isMenuChangeModOn) {
                LinearLayout selectIc = v.findViewWithTag("selectIc");
                int selectId = v.getId();

                if(selectIc.getVisibility() == View.GONE) {
                    tempSelectedCategoryMap.put(selectId, tempCategoryMap.get(selectId));
//                    selectIc.setVisibility(View.VISIBLE);
                    switchCategoryIcSelect(selectIc, true);
                } else {
                    tempSelectedCategoryMap.remove(selectId);
//                    selectIc.setVisibility(View.GONE);
                    switchCategoryIcSelect(selectIc, false);
                }

                switchMenuChangeMod();
            }
        }
    }

    @Override
    public void noteClick(View v) {
        if(!isChangeModOn) {
            Note selectNote = tempNoteMap.get(v.getId());

            String noteText, noteSpot, noteDate, noteCategory, tempCategory;

            noteText = selectNote.getText();
            noteSpot = selectNote.getSpot();
            noteDate = selectNote.getDate();
            noteCategory = selectNote.getCategory();
            tempCategory = currentCategory;
            int noteId = selectNote.getId();

            List<Tag> noteTags;

            if(selectNote.getTags() != null) noteTags = selectNote.getTags();
            else noteTags = new ArrayList<>();

            noteIntent.putExtra("noteId", noteId);
            noteIntent.putExtra("noteText", noteText);
            noteIntent.putExtra("noteSpot", noteSpot);

            Log.d(LOG, "----------------" + currentCategory);
            Log.d(LOG, "----------------" + getResources().getString(R.string.all_notes));

            if(tempCategory.equalsIgnoreCase(getResources().getString(R.string.all_notes))) {
                noteIntent.putExtra("category", "");
            } else {
                noteIntent.putExtra("category", currentCategory);
            }

            if (!noteCategory.equalsIgnoreCase("")) noteIntent.putExtra("noteCategory", noteCategory);
            else noteIntent.putExtra("noteCategory", "");

            noteIntent.putExtra("noteDate", noteDate);

            if (noteTags.size() != 0) {
                String[] noteTagNames = new String[noteTags.size()];

                for (int i = 0; i < noteTagNames.length; i++)
                    noteTagNames[i] = noteTags.get(i).getName();

                noteIntent.putExtra("noteTagNames", noteTagNames);
            } else {
                noteIntent.putExtra("noteTagNames", new String[]{});
            }

            noteIntent.putExtra("isExist", true);

            startActivity(noteIntent);
        } else {
            LinearLayout switchIcon = v.findViewWithTag("switch");

            if(switchIcon.getVisibility() == View.GONE) {
                switchIcon.setVisibility(View.VISIBLE);

                Animation inAnimation = AnimationUtils.loadAnimation(this, R.anim.select_item_in_anim);
                switchIcon.startAnimation(inAnimation);

                int id = v.getId();
                tempSelectedMap.put(id, tempNoteMap.get(id));
            } else {
                tempSelectedMap.remove(v.getId());

                Animation outAnimation = AnimationUtils.loadAnimation(this, R.anim.select_item_out_anim);
                switchIcon.startAnimation(outAnimation);

                switchIcon.setVisibility(View.GONE);
            }

            if(tempSelectedMap.size() > 0) titleNote.setText(getResources().getString(R.string.selected) + " " + tempSelectedMap.size());
            else setCurrentCategory(currentCategory);
        }
    }

    @Override
    public void noteLongPress(View v) {
        if(!isChangeModOn) {
            LinearLayout switchIcon = v.findViewWithTag("switch");

            if(switchIcon.getVisibility() == View.GONE) {
                switchIcon.setVisibility(View.VISIBLE);
                int id = v.getId();

                Animation inAnimation = AnimationUtils.loadAnimation(this, R.anim.select_item_in_anim);
                switchIcon.startAnimation(inAnimation);

                tempSelectedMap.put(id, tempNoteMap.get(id));
            } else {
                tempSelectedMap.remove(v.getId());

                Animation outAnimation = AnimationUtils.loadAnimation(this, R.anim.select_item_out_anim);
                switchIcon.startAnimation(outAnimation);

                switchIcon.setVisibility(View.GONE);
            }

            if(tempSelectedMap.size() > 0) titleNote.setText(getResources().getString(R.string.selected) + " " + tempSelectedMap.size());
            else titleNote.setText(currentCategory);

            changeBtn.setText(getResources().getString(R.string.cancel));

            noteBtnAnim(true);

            isChangeModOn = true;
        }
    }

    @Override
    public void showNotes(List<Note> notes, Context context, LinearLayout container) {
        container.removeAllViews();

        if (tempNoteMap.size() > 0) tempNoteMap.clear();

        if(notes.size() == 0) {
            int dp24 = (int)dpToPixel(24);

            //box
            LinearLayout box = new LinearLayout(context);
            LinearLayout.LayoutParams boxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            boxParams.leftMargin = dp24;
            boxParams.topMargin = dp24;
            box.setLayoutParams(boxParams);
            box.setOrientation(LinearLayout.HORIZONTAL);

            //text
            TextView text = new TextView(context);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textParams.gravity = Gravity.CENTER_VERTICAL;
            text.setLayoutParams(textParams);
            text.setText(getResources().getString(R.string.no_notes));
            text.setTextSize(13);
            text.setLineSpacing(1, (float)1.4);
            text.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));

            //smileIc
            ImageView smileIc = new ImageView(context);
            LinearLayout.LayoutParams smileParams = new LinearLayout.LayoutParams(dp24, dp24);
            smileParams.leftMargin = (int)dpToPixel(10);
            smileParams.gravity = Gravity.CENTER_VERTICAL;
            smileIc.setLayoutParams(smileParams);
            smileIc.setBackground(getResources().getDrawable(R.drawable.ic_smile));

            //implement
            box.addView(text);
            box.addView(smileIc);
            container.addView(box);
        } else {
            for (Note n : notes) {
                //tech
                List<Tag> tags = n.getTags();

                //container
                LinearLayout layoutContainer = new LinearLayout(context);
                layoutContainer.setOrientation(LinearLayout.HORIZONTAL);
                layoutContainer.setFocusable(true);
                layoutContainer.setClickable(true);
//                LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(59));
                LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(54));
                layoutContainer.setLayoutParams(containerParams);

                //changeContainer
                LinearLayout changeContainer = new LinearLayout(context);
                changeContainer.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams changeContainerParams = new LinearLayout.LayoutParams((int)dpToPixel(20), ViewGroup.LayoutParams.MATCH_PARENT);
                changeContainer.setLayoutParams(changeContainerParams);
                //changeIcon
                LinearLayout changeIcon = new LinearLayout(context);
                LinearLayout.LayoutParams changeIconParams = new LinearLayout.LayoutParams((int)dpToPixel(8), LinearLayout.LayoutParams.MATCH_PARENT);
                changeIcon.setLayoutParams(changeIconParams);
                changeIcon.setBackgroundColor(mapColor.get(ThemeConstants.AC_2));
                changeIcon.setVisibility(View.GONE);
                changeIcon.setTag("switch");

                //contentLayout
                LinearLayout contentContainer = new LinearLayout(context);
                contentContainer.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.75f);
                contentContainer.setLayoutParams(contentParams);
                //text
                RelativeLayout textBox = new RelativeLayout(context);
                RelativeLayout.LayoutParams textBoxParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(29));
                textBox.setLayoutParams(textBoxParams);

                TextView textView = new TextView(context);
                RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                if(tags.size() >= 1 && !(tags.get(0).getName().equalsIgnoreCase(""))) textViewParams.bottomMargin = (int)dpToPixel(4);
                textView.setLayoutParams(textViewParams);
                textView.setText(getValidNoteStr(n.getText()));
                textView.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));

                //tagContainer
                FlowLayout tagContainer = new FlowLayout(context);
                FlowLayout.LayoutParams tagContainerParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(25));
                tagContainer.setLayoutParams(tagContainerParams);
                tagContainer.setTag("hide");

                    //tags
                for (Tag t : tags) {
                    String tagName = t.getName();

                    if(!tagName.equalsIgnoreCase("")) {
                        LinearLayout tagMarginBox = new LinearLayout(context);
                        LinearLayout.LayoutParams tagMarginParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        tagMarginBox.setLayoutParams(tagMarginParams);
                        tagMarginBox.setPadding(0, 0, (int)dpToPixel(10), (int)dpToPixel(5));

                        LinearLayout tagBox = new LinearLayout(context);
                        LinearLayout.LayoutParams tagBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tagBox.setLayoutParams(tagBoxParams);
                        tagBox.setPadding((int)dpToPixel(10), 0, (int)dpToPixel(10), (int)dpToPixel(2));
                        tagBox.setBackgroundResource(R.drawable.tag_light_shape_ripple);
                        tagBox.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_3), PorterDuff.Mode.SRC_IN);

                        TextView tagView = new TextView(context);
                        ViewGroup.LayoutParams tagViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tagView.setLayoutParams(tagViewParams);
                        tagView.setText(tagName);
                        tagView.setTextSize(11);
                        if(tagName.length() >= 32) {
                            tagView.setTextSize(9);
                            tagBox.setPadding((int)dpToPixel(10), (int)dpToPixel(2), (int)dpToPixel(10), (int)dpToPixel(4));
                        }
                        tagView.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));

                        tagBox.addView(tagView);
                        tagMarginBox.addView(tagBox);
                        tagContainer.addView(tagMarginBox);

                        //onClick
                        View.OnClickListener tagOnClick = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //tagBox
                                //tagContainer
                                //contentContainer
                                //layoutContainer
                                LinearLayout marginBox = (LinearLayout) v.getParent();
                                FlowLayout tagContainer = (FlowLayout) marginBox.getParent();
                                LinearLayout contentContainer = (LinearLayout) tagContainer.getParent();
                                LinearLayout layoutContainer = (LinearLayout) contentContainer.getParent();

                                if(tagContainer.getTag().toString().equalsIgnoreCase("hide")) {
                                    LinearLayout.LayoutParams tagContainerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tagContainer.setLayoutParams(tagContainerParams);

                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    layoutContainer.setLayoutParams(layoutParams);

                                    tagContainer.setTag("show");
                                } else {
                                    LinearLayout.LayoutParams tagContainerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(25));
                                    tagContainer.setLayoutParams(tagContainerParams);

                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(54));
                                    layoutContainer.setLayoutParams(layoutParams);

                                    tagContainer.setTag("hide");
                                }
                            }
                        };

                        tagBox.setOnClickListener(tagOnClick);
                    }
                }

                //spot
                String spotPath = n.getSpot();

                RelativeLayout spotBox = new RelativeLayout(context);
                LinearLayout.LayoutParams spotBoxParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.15f);
                spotBox.setLayoutParams(spotBoxParams);

                try {
                    if(!spotPath.equalsIgnoreCase("")) {
                        //image
                        ImageView spotView = new ImageView(context);

                        try {
                            InputStream inputStream = getAssets().open(spotPath);
                            Bitmap bm = BitmapFactory.decodeStream(inputStream);
                            spotView.setImageBitmap(bm);
                            inputStream.close();
                        } catch (IOException e) {}

                        RelativeLayout.LayoutParams spotViewParams = new RelativeLayout.LayoutParams((int)dpToPixel(36), (int)dpToPixel(36));
                        spotViewParams.addRule(RelativeLayout.CENTER_VERTICAL);
                        spotViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        spotView.setLayoutParams(spotViewParams);

                        spotBox.addView(spotView);
                    }
                } catch (NullPointerException e) {}

                //set view id
                int containerId = View.generateViewId();

                if(isChangeModOn){
                    int noteId = n.getId();

                    for (Map.Entry<Integer, Note> pair : tempSelectedMap.entrySet())
                        if(pair.getValue().getId() == noteId) containerId = pair.getKey();
                }

                layoutContainer.setId(containerId);
                tempNoteMap.put(containerId, n);

                //touchListener
                MenuNoteTouchListener touchListener = new MenuNoteTouchListener(this,this, layoutContainer);
                layoutContainer.setOnTouchListener(touchListener);

                //downBorder
                LinearLayout downBorder = new LinearLayout(context);
                LinearLayout.LayoutParams borderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(1));
                downBorder.setLayoutParams(borderParams);
                downBorder.setBackgroundColor(mapColor.get(ThemeConstants.BACK_4));

                //implement
                changeContainer.addView(changeIcon);
                layoutContainer.addView(changeContainer);

                textBox.addView(textView);
                contentContainer.addView(textBox);

                contentContainer.addView(tagContainer);
                layoutContainer.addView(contentContainer);

                layoutContainer.addView(spotBox);
                //final
                container.addView(layoutContainer);
                container.addView(downBorder);
            }

            try {
                if(isChangeModOn) {
                    for (Map.Entry<Integer, Note> entry : tempSelectedMap.entrySet())
                        findViewById(entry.getKey()).findViewWithTag("switch").setVisibility(View.VISIBLE);

                    if(tempSelectedMap.size() > 0) titleNote.setText(getResources().getString(R.string.selected) + " " + tempSelectedMap.size());
                    else titleNote.setText(currentCategory);
                }
            } catch (NullPointerException e) {
                Log.d("log", "NullPointerException in refresh switch icon -------------");
            }
        }
    }

    private String getValidNoteStr(String title) {
        while(true) {
            if(title.indexOf("\n") == 0) {
                if(title.length() == 1) {
                    title = "";
                } else {
                    title = title.substring(1, title.length());
                }
            } else {
                break;
            }
        }

        if (title.contains("\n")) {
            title = title.substring(0, title.indexOf("\n"));
        }

        if(title.length() > 29) {
            title = title.substring(0, 28);

            if (title.contains(" ")) {
                title = title.substring(0, title.lastIndexOf(" "));
                title += "...";
            } else {
                title += "...";
            }
        }

        return title;
    }

    @Override
    public void showCategory(List<Category> categories, Context context, LinearLayout container) {
        container.removeAllViews();
        if(tempCategoryMap.size() > 0) tempCategoryMap.clear();

        if(categories.size() == 0) {
            //box
            LinearLayout textBox = new LinearLayout(context);
            LinearLayout.LayoutParams textBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textBox.setLayoutParams(textBoxParams);
            textBox.setPadding((int)dpToPixel(18), (int)dpToPixel(18), 0, 0);

            //textView
            TextView textView = new TextView(context);
            ViewGroup.LayoutParams textParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(textParams);
            textView.setText(getResources().getString(R.string.no_categories));
            textView.setTextSize(12);
            textView.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));

            //sadIc
            ImageView sadIc = new ImageView(context);
            ViewGroup.LayoutParams sadParams = new ViewGroup.LayoutParams((int)dpToPixel(16), (int)dpToPixel(16));
            sadIc.setLayoutParams(sadParams);
            sadIc.setBackground(getResources().getDrawable(R.drawable.ic_sad));

            //marginBox
            LinearLayout marginBox = new LinearLayout(context);
            LinearLayout.LayoutParams marginParams = new LinearLayout.LayoutParams((int)dpToPixel(5), (int)dpToPixel(20));
            marginBox.setLayoutParams(marginParams);

            //implement
            textBox.addView(textView);
            textBox.addView(marginBox);
            textBox.addView(sadIc);
            container.addView(textBox);
        } else {
            for (Category c : categories) {
                //boxLayout
                LinearLayout boxLayout = new LinearLayout(context);
                boxLayout.setOrientation(LinearLayout.HORIZONTAL);
                boxLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(60)));
                boxLayout.setFocusable(true);
                boxLayout.setClickable(true);

                //selectBox
                LinearLayout selectBox = new LinearLayout(context);
                LinearLayout.LayoutParams selectBoxParams = new LinearLayout.LayoutParams((int)dpToPixel(20), LinearLayout.LayoutParams.MATCH_PARENT);
                selectBox.setLayoutParams(selectBoxParams);
                //selectIc
                LinearLayout selectIc = new LinearLayout(context);
                selectIc.setLayoutParams(new LinearLayout.LayoutParams((int)dpToPixel(6), LinearLayout.LayoutParams.MATCH_PARENT));
                selectIc.setBackgroundColor(mapColor.get(ThemeConstants.AC_1));
                selectIc.setTag("selectIc");
                selectIc.setVisibility(View.GONE);

                //nameView
                LinearLayout nameBox = new LinearLayout(this);
                LinearLayout.LayoutParams nameBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.8f);
                nameBox.setLayoutParams(nameBoxParams);

                //nameMarginView
                LinearLayout nameMarginBox = new LinearLayout(context);
                LinearLayout.LayoutParams nameMarginParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                nameMarginParams.gravity = Gravity.CENTER_VERTICAL;
                nameMarginParams.bottomMargin = (int)dpToPixel(2);
                nameMarginBox.setLayoutParams(nameMarginParams);

                TextView nameView = new TextView(context);
                nameView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                nameView.setTextSize(14);
                nameView.setText(c.getName());
                nameView.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));
                nameView.setTag("categoryName");

                //spot
                boolean isSpotExist = false;
                RelativeLayout layoutSpot = new RelativeLayout(context);
                LinearLayout.LayoutParams paramsSpot = (LinearLayout.LayoutParams) new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.1f);
                layoutSpot.setLayoutParams(paramsSpot);
                layoutSpot.setVisibility(View.GONE);

                String spotPath = c.getSpot();

                try {
                    if(!spotPath.equalsIgnoreCase("")) {
                        ImageView spotIc = new ImageView(context);
                        RelativeLayout.LayoutParams spotIcParams = new RelativeLayout.LayoutParams((int)dpToPixel(30),(int)dpToPixel(30));
                        spotIcParams.addRule(RelativeLayout.CENTER_VERTICAL);
                        spotIcParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        spotIc.setLayoutParams(spotIcParams);

                        try {
                            InputStream inputStream = getAssets().open(spotPath);
                            Bitmap bm = BitmapFactory.decodeStream(inputStream);
                            spotIc.setImageBitmap(bm);
                            inputStream.close();
                        } catch (IOException e) {}

                        layoutSpot.addView(spotIc);
                        layoutSpot.setVisibility(View.VISIBLE);
                        LinearLayout.LayoutParams nameBoxParamsWithSpot = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.7f);
                        nameBox.setLayoutParams(nameBoxParamsWithSpot);

                        isSpotExist = true;
                    }
                } catch (NullPointerException e) {}

                //set view id
                int containerId = View.generateViewId();

                if(isMenuChangeModOn){
                    int categoryId = c.getId();

                    for (Map.Entry<Integer, Category> entry : tempSelectedCategoryMap.entrySet())
                        if(entry.getValue().getId() == categoryId) containerId = entry.getKey();
                }

                boxLayout.setId(containerId);
                tempCategoryMap.put(containerId, c);
                //

                //TouchListener
                MenuCategoryTouchListener touchListener = new MenuCategoryTouchListener(this, this, boxLayout);
                boxLayout.setOnTouchListener(touchListener);

                //amountBox
                RelativeLayout amountBox = new RelativeLayout(context);
                amountBox.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f));

                LinearLayout amountGravityBox = new LinearLayout(context);
                RelativeLayout.LayoutParams amountGravityBoxParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                amountGravityBoxParams.addRule(RelativeLayout.CENTER_VERTICAL);
                amountGravityBoxParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                amountGravityBox.setLayoutParams(amountGravityBoxParams);
                amountGravityBox.setOrientation(LinearLayout.HORIZONTAL);

                TextView amountView = new TextView(context);
                LinearLayout.LayoutParams amountViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                amountViewParams.gravity = Gravity.CENTER_VERTICAL;
                amountView.setLayoutParams(amountViewParams);
                amountView.setTextSize(12);
                amountView.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));
                amountView.setText(Integer.toString(c.getNoteAmount()));

                ImageView amountIc = new ImageView(context);
                LinearLayout.LayoutParams amountIcParams = new LinearLayout.LayoutParams((int)dpToPixel(17), (int)dpToPixel(17));
                amountIcParams.gravity = Gravity.CENTER_VERTICAL;
                amountIc.setLayoutParams(amountIcParams);
                amountIc.setBackground(getResources().getDrawable(R.drawable.ic_triangle_right));
                amountIc.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_DARK_2), PorterDuff.Mode.SRC_IN);

                //downBorder
                LinearLayout downBorder = new LinearLayout(context);
                LinearLayout.LayoutParams downBorderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(1));
                downBorder.setLayoutParams(downBorderParams);
                downBorder.setBackgroundColor(mapColor.get(ThemeConstants.BACK_DARK_LIGHT));

                //sizeCorrect
                if(screenXSize < screenYSize) {
                    if(isSpotExist) {
                        if(nameView.getText().toString().length() >= 20) nameView.setTextSize(12);
                    } else {
                        if(nameView.getText().toString().length() >= 22) nameView.setTextSize(12);
                    }
                }

                //implementation
                selectBox.addView(selectIc);
                boxLayout.addView(selectBox);
                nameMarginBox.addView(nameView);
                nameBox.addView(nameMarginBox);
                boxLayout.addView(nameBox);
                boxLayout.addView(layoutSpot);

                /*amountMarginBox.addView(amountView);
                amountIcMarginBox.addView(amountIc);
                amountMarginBox.addView(amountIcMarginBox);
                amountBox.addView(amountMarginBox);*/
                amountGravityBox.addView(amountView);
                amountGravityBox.addView(amountIc);
                amountBox.addView(amountGravityBox);

                boxLayout.addView(amountBox);
                container.addView(boxLayout);
                container.addView(downBorder);

                try {
                    if(isMenuChangeModOn) {
                        for (Map.Entry<Integer, Category> entry : tempSelectedCategoryMap.entrySet())
                            findViewById(entry.getKey()).findViewWithTag("selectIc").setVisibility(View.VISIBLE);
                    }
                } catch (NullPointerException e) {
                    Log.d("log", "NullPointerException in refresh switch icon -------------");
                }
            }
        }
    }

    @Override
    public void showTag(List<Tag> tags, Context context, FlowLayout container) {
        container.removeAllViews();

//        List<View> tempViewList = new ArrayList<>();
        TransitionSet set = new TransitionSet();
        set.addTransition(new Slide(Gravity.RIGHT));
        set.setDuration(450);
        set.setInterpolator(new AccelerateDecelerateInterpolator());

        TransitionManager.beginDelayedTransition(container, set);

        if(tags.size() == 0) {
            //box
            LinearLayout textBox = new LinearLayout(context);
            LinearLayout.LayoutParams boxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textBox.setLayoutParams(boxParams);
            textBox.setPadding((int)dpToPixel(18), (int)dpToPixel(18), 0, 0);

            //text
            TextView textView = new TextView(context);
            ViewGroup.LayoutParams textParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(textParams);
            textView.setText(getResources().getString(R.string.no_tags));
            textView.setTextSize(14);
            textView.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));

            //sadIc
            ImageView sadIc = new ImageView(context);
            ViewGroup.LayoutParams sadParams = new ViewGroup.LayoutParams((int)dpToPixel(20), (int)dpToPixel(20));
            sadIc.setLayoutParams(sadParams);
            sadIc.setBackground(getResources().getDrawable(R.drawable.ic_sad));

            //marginBox
            LinearLayout marginBox = new LinearLayout(context);
            LinearLayout.LayoutParams marginParams = new LinearLayout.LayoutParams((int)dpToPixel(6), (int)dpToPixel(20));
            marginBox.setLayoutParams(marginParams);

            //implement
            textBox.addView(textView);
            textBox.addView(marginBox);
            textBox.addView(sadIc);
            container.addView(textBox);
        } else {
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
                tagBoxParams.rightMargin = (int)dpToPixel(14);
                tagBoxParams.topMargin = (int)dpToPixel(12);
                tagBox.setLayoutParams(tagBoxParams);
                tagBox.setPadding((int)dpToPixel(15), 0, (int)dpToPixel(15), (int)dpToPixel(3));
                tagBox.setBackgroundResource(R.drawable.tag_dark_shape_ripple);
                tagBox.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_DARK), PorterDuff.Mode.SRC_IN);
                tagBox.setTag("tagBox");

                //tagName
                TextView tagView = new TextView(context);
                ViewGroup.LayoutParams tagViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tagView.setLayoutParams(tagViewParams);
                tagView.setText(tagName);
                tagView.setTextSize(14);
                tagView.setTextColor(mapColor.get(ThemeConstants.TEXT_DARK));
                tagView.setTag("tagName");

                //onClick
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isHideMenuShow) {
                            TextView tagBox = v.findViewWithTag("tagName");
                            String tagName = tagBox.getText().toString();

                            showNotes(mainPresenter.getNoteByTag(tagName), v.getContext(), containerNote);

                            setCurrentCategory("");

                            closeMenu();
                        }
                    }
                };

                tagBox.setOnClickListener(onClickListener);

                //sizeCorrect
                if(screenYSize > screenXSize) {
                    if (tagName.length() >= 24 && tagName.length() < 28) {
                        tagView.setTextSize(12);
                        tagBox.setPadding((int)dpToPixel(15), 0, (int)dpToPixel(15), (int)dpToPixel(3));
                    } else if (tagName.length() >= 28 && tagName.length() < 32) {
                        tagView.setTextSize(10);
                        tagBox.setPadding((int)dpToPixel(15), (int)dpToPixel(2), (int)dpToPixel(15), (int)dpToPixel(4));
                    } else if (tagName.length() >= 28) {
                        tagView.setTextSize(8);
                        tagBox.setPadding((int)dpToPixel(15), (int)dpToPixel(5), (int)dpToPixel(15), (int)dpToPixel(6));
                    } else {
                        tagView.setTextSize(14);
                        tagBox.setPadding((int)dpToPixel(15), 0, (int)dpToPixel(15), (int)dpToPixel(3));
                    }
                } else {
                    if (tagName.length() >= 38) {
                        tagView.setTextSize(12);
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

    public int countNotes() {
        return mainPresenter.getAllNotes().size();
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

    private void closeChangeMod() {
        if(isChangeModOn) {
            switchChangeModContainer(-2);
            changeBtn.setText(getResources().getString(R.string.change));

            for (Map.Entry<Integer, Note> entry : tempSelectedMap.entrySet()) {
                LinearLayout switchIcon = findViewById(entry.getKey());
                if(switchIcon != null) {
                    LinearLayout selectIc = switchIcon.findViewWithTag("switch");
                    Animation outIcAnimation = AnimationUtils.loadAnimation(this, R.anim.select_item_out_anim);
                    selectIc.startAnimation(outIcAnimation);

                    selectIc.setVisibility(View.GONE);
                }
            }

            if(tempSelectedMap.size() != 0) tempSelectedMap.clear();

            isChangeContainerShow = false;

            setCurrentCategory(currentCategory);

            changeModTagExistList.setVisibility(View.GONE);

            noteBtnAnim(false);

            isChangeModOn = false;
        }
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
                if(number != -1) {
                    for (View v : changeModContainersList)
                        v.setVisibility(View.GONE);
                }

                for (View v : changeModBtnsList) {
                    LinearLayout selectIc = v.findViewWithTag("ic_select");
                    selectIc.setAnimation(selectOutAnim);
                    selectIc.setVisibility(View.GONE);

                    ImageView ic = v.findViewWithTag("ic");
                    ic.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_IC_COLOR), PorterDuff.Mode.SRC_IN);
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

                    ImageView ic = changeModBtnsList.get(number).findViewWithTag("ic");
//                    ic.getBackground().setColorFilter(getResources().getColor(R.color.back_1), PorterDuff.Mode.SRC_IN);
                    ic.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_1), PorterDuff.Mode.SRC_IN);

                    changeModContainersList.get(number).setVisibility(View.VISIBLE);

                    cacheChangeMod = number;
                }
            } else {
                if (number != -2) changeModOpenAnim(false);
                else changeModOpenSwitcher(false);

                changeModTagExistList.setVisibility(View.GONE);
                isChangeContainerShow = false;
            }
        }
    }

    private void switchMenuTagBox() {
        if(!isHideMenuShow) {

            TransitionManager.beginDelayedTransition(menuMainView, new ChangeBounds()
                    .setDuration(300)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .excludeChildren(menuScrollTagBox, true));

            TransitionManager.beginDelayedTransition(tagAngelDownBox, new Rotate()
                    .setDuration(300)
                    .setInterpolator(new AccelerateDecelerateInterpolator()));

            if(!tagBtnSwither) {
                tagAngelDownBox.setRotation(-180f);

                menuScrollTagBox.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams tagBoxParams = (LinearLayout.LayoutParams) menuScrollTagBox.getLayoutParams();
                tagBoxParams.height = (int)dpToPixel(250);
                tagBoxParams.topMargin = 0;
                menuScrollTagBox.setLayoutParams(tagBoxParams);

                categoryEtContainer.setVisibility(View.GONE);
                tagEtContainer.setVisibility(View.VISIBLE);

                refreshTagList(0, null);

                tagBtnSwither = true;
            } else {
                tagAngelDownBox.setRotation(0f);

                LinearLayout.LayoutParams tagBoxParams = (LinearLayout.LayoutParams) menuScrollTagBox.getLayoutParams();
                tagBoxParams.height = 0;
                tagBoxParams.topMargin = -(int)dpToPixel(250);
                menuScrollTagBox.setLayoutParams(tagBoxParams);
                menuScrollTagBox.setVisibility(View.GONE);

                tagEtContainer.setVisibility(View.GONE);
                categoryEtContainer.setVisibility(View.VISIBLE);

                tagBtnSwither = false;
            }
        }
    }

    public void changeModLoadList(LinearLayout zone) {
        switch(zone.getId()) {
            case R.id.changeModCategory :
                changeModLoadCategory(this, changeModCategoryList, mainPresenter.getAllCategory());
                break;
            case R.id.changeModTag :
                changeModLoadTags(this, changeModTagList, mainPresenter.getAllTag());
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

    @Override
    public void changeModUpdateCategory(String categoryName) {
        if(!categoryName.equalsIgnoreCase("")) {
            if(tempSelectedMap.size() > 0) {
                for(Map.Entry<Integer, Note> pair : tempSelectedMap.entrySet()) {
                    Note note = pair.getValue();

                    mainPresenter.checkCategoryExist(categoryName);

                    if(!note.getCategory().equals(categoryName)) mainPresenter.updateNoteAmount(note.getCategory(), categoryName);

                    mainPresenter.updateNote(note.getId(), note.getText(), mainPresenter.getTagStr(note.getTags()), categoryName, new Date(), note.getSpot());

                    note.setCategory(categoryName);

                    pair.setValue(note);
                }

                changeCategoryEt.setText("");
                showToast(ToastWarnings.ADD_CATEGORY_WITH_NAME, categoryName);

                changeModLoadCategory(this, changeModCategoryList, mainPresenter.getAllCategory());
                updateNoteList();

                refreshMenuCategoryList("all", null);
            }
        }
    }

    @Override
    public void changeModRemoveCategory() {
        if(tempSelectedMap.size() > 0) {
            for(Map.Entry<Integer, Note> pair : tempSelectedMap.entrySet()) {
                Note note = pair.getValue();

                mainPresenter.updateNoteAmount(note.getCategory(), "");

                mainPresenter.updateNote(note.getId(), note.getText(), mainPresenter.getTagStr(note.getTags()), "", new Date(), note.getSpot());

                note.setCategory("");
                pair.setValue(note);
            }

            showToast(ToastWarnings.CATEGORY_REMOVED, null);

            closeChangeMod();

            updateNoteList();

            refreshMenuCategoryList("all", null);
        }
    }

    @Override
    public void changeModUpdateTag(String tagStr) {
        if(isChangeModOn) {
            if(!tagStr.equalsIgnoreCase("") && tempSelectedMap.size() > 0) {
                if(mainPresenter.isValidateTagString(tagStr)) {
                    List<Tag> tagsByName = mainPresenter.getTagByName(tagStr);

                    if(tagsByName.size() == 0)
                        mainPresenter.addTag(tagStr);

                    for(Map.Entry<Integer, Note> pair : tempSelectedMap.entrySet()) {
                        Note note = pair.getValue();

                        String tag = "";
                        boolean isExist = false;

                        List<Tag> tags = note.getTags();

                        if(tags.size() > 0) {
                            for (Tag t : tags) {
                                String tagName = t.getName();
                                if(tagName.equals(tagStr)) isExist = true;
                                if(!tagName.equalsIgnoreCase("")) tag += tagName + "@@";
                            }
                        }

                        tag += tagStr;

                        if(!isExist) {
                            mainPresenter.updateNote(note.getId(), note.getText(), tag, note.getCategory(), new Date(), note.getSpot());

                            Tag newTag = new Tag();
                            newTag.setName(tagStr);
                            tags.add(newTag);
                            note.setTags(tags);
                        }
                    }

                    changeTagEt.setText("");
                    showToast(ToastWarnings.ADD_TAG_WITH_NAME, tagStr);

                    updateNoteList();
                    refreshTagList(0, null);
                    changeModLoadTags(this, changeModTagList, mainPresenter.getAllTag());
                    changeModLoadExistTags(this, changeModTagExistList);
                } else {
                    showError(ToastErrors.NOT_VALID_TAG_NAME);
                }
            }
        }
    }

    @Override
    public void changeModRemoveTag(String tagName, boolean isSingle) {
        if(!tagName.equalsIgnoreCase("")) {
            for(Map.Entry<Integer, Note> entry : tempSelectedMap.entrySet()) {
                Note note = entry.getValue();
                boolean isExist = false;
                List<Tag> tags = note.getTags();

                for(Tag t : tags){
                    if(t.getName().equalsIgnoreCase(tagName)) {
                        isExist = true;
                        tags.remove(tags.indexOf(t));
                        break;
                    }
                }

                if(isExist) {
                    List<Note> notesByTag = mainPresenter.getNoteByTag(tagName);
                    if(notesByTag.size() <= 1) mainPresenter.removeTag(tagName);

                    String tagStr = "";

                    for(Tag t : tags)
                        if(!t.getName().equalsIgnoreCase("")) tagStr += t.getName() + "@@";

                    if(tagStr.length() > 0) tagStr = tagStr.substring(0, tagStr.length()-2);

                    mainPresenter.updateNote(note.getId(), note.getText(), tagStr, note.getCategory(), new Date(), note.getSpot());

                    note.setTags(tags);
                    entry.setValue(note);
                }
            }
        }

        if(isSingle) showToast(ToastWarnings.TAG_REMOVED, null);

        updateNoteList();
        changeModLoadExistTags(this, changeModTagExistList);
        refreshTagList(0, null);

        updateNoteList();
    }

    public void changeModLoadTags(Context context, FlowLayout container, List<Tag> tags) {
        container.removeAllViews();

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

    public void changeModLoadExistTags(Context context, LinearLayout container) {
        container.removeAllViews();
        List<String> existTagNames = new ArrayList<>();

        for (Map.Entry<Integer, Note> entry : tempSelectedMap.entrySet()) {
            for(Tag t : entry.getValue().getTags()) {
                String tagName = t.getName();
                if(!existTagNames.contains(tagName) && !tagName.equalsIgnoreCase("")) existTagNames.add(tagName);
            }
        }

        TransitionManager.beginDelayedTransition(container, new TransitionSet()
                .setDuration(400)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .addTransition(new Slide(Gravity.RIGHT))
                .addTransition(new Fade()));

        if(existTagNames.size() > 0) container.setVisibility(View.VISIBLE);
        else container.setVisibility(View.GONE);

        for(String s : existTagNames) {
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

                    changeModRemoveTag(textTag.getText().toString(), true);
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

    public void changeModUpdateSpot(String spotId) {
        for(Map.Entry<Integer, Note> entry : tempSelectedMap.entrySet()){
            Note note = entry.getValue();
            note.setSpot(spotId);
            mainPresenter.updateNote(note.getId(), note.getText(), mainPresenter.getTagStr(note.getTags()), note.getCategory(), new Date(), spotId);
            entry.setValue(note);
        }

        changeModLoadCurrentSpot(this, changeModSpotCurrentContainer);
        updateNoteList();
    }

    private void changeModLoadCurrentSpot(Context context, LinearLayout container) {
        container.removeAllViews();

        List<String> currentSpotIdList = new ArrayList<>();

        for(Map.Entry<Integer, Note> entry : tempSelectedMap.entrySet()) {
            String spotPath = entry.getValue().getSpot();
            if(!currentSpotIdList.contains(spotPath)) currentSpotIdList.add(spotPath);
        }

        TransitionManager.beginDelayedTransition(container, new TransitionSet()
                .setDuration(250)
                .addTransition(new Scale(0.4f))
                .addTransition(new Fade())
                .setInterpolator(new AccelerateDecelerateInterpolator()));

        for(String s : currentSpotIdList) {
            if(!s.equalsIgnoreCase("")) {
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
                    InputStream inputStream = getAssets().open(s);
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);
                    spotView.setImageBitmap(bm);
                    inputStream.close();
                } catch (IOException e) {}

                ViewGroup.LayoutParams spotParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                spotView.setLayoutParams(spotParams);

                //addToContainer
                spotBox.addView(spotView);
                container.addView(spotBox);            }
        }
    }

    private void updateNoteList() {
        if(currentCategory.equalsIgnoreCase(getResources().getString(R.string.all_notes)) || currentCategory.equalsIgnoreCase("")) {
            showNotes(mainPresenter.getAllNotes(), this, containerNote);
        } else showNotes(mainPresenter.getNoteByCategory(currentCategory), context, containerNote);
    }

    private void updateNoteList(boolean isAll) {
        if(isAll) {
            showNotes(mainPresenter.getAllNotes(), context, containerNote);
            currentCategory = "";
            titleNote.setText(getResources().getText(R.string.all_notes));
        }
    }

    private void switchMenuChangeMod() {
        if(!isHideMenuShow) {
            if(!isMenuChangeModOn) {
                changeMenuBarAnim(true, true);

                changeMenuBtn.setText(getResources().getString(R.string.cancel));

                isMenuChangeModOn = true;
            } else {
                closeCategoryChangeMod();
            }
        } else {
            for (View v : changeModMenuContainersList)
                v.setVisibility(View.GONE);

            menuChangeHideContainer.setVisibility(View.GONE);
            categoryNameEt.setEnabled(true);
            tagNameEt.setEnabled(true);
            changeMenuBtn.setText(getResources().getString(R.string.cancel));

            clearAllSelectIcChangeModMenu(true);

            isHideMenuShow = false;
            cacheMenuMod = -1;
        }
    }

    public void switchHideMenu(int number) {
        if(isMenuChangeModOn && number != cacheMenuMod) {
            menuChangeHideContainer.setVisibility(View.VISIBLE);
            isHideMenuShow = true;

            categoryNameEt.setEnabled(false);
            tagNameEt.setEnabled(false);
            changeMenuBtn.setText(getResources().getString(R.string.close));

            clearAllSelectIcChangeModMenu(true);

            Animation hideMenuAnim = AnimationUtils.loadAnimation(this, R.anim.opacity_out_anim);
            for (View v : changeModMenuContainersList) {
                if(v.getVisibility() == View.VISIBLE) {
                    v.setAnimation(hideMenuAnim);
                    v.setVisibility(View.GONE);
                }
            }

            //selectIc
            LinearLayout icSelect = changeModMenuBtnsList.get(number).findViewWithTag("ic_select");
            changeModMenuBtnsList.get(number).findViewWithTag("ic").getBackground().setColorFilter(mapColor.get(ThemeConstants.DEEP_DARK), PorterDuff.Mode.SRC_IN);
            Animation showIcAnim = AnimationUtils.loadAnimation(this, R.anim.changemod_bar_select_ic_in_anim);
            icSelect.setAnimation(showIcAnim);
            RelativeLayout.LayoutParams icParams = (RelativeLayout.LayoutParams) icSelect.getLayoutParams();
            icParams.bottomMargin = 0;
            icSelect.setLayoutParams(icParams);

            //container
            LinearLayout changeMenu = (LinearLayout) changeModMenuContainersList.get(number);
            Animation popupShowAnim = AnimationUtils.loadAnimation(this, R.anim.popup_show_anim);
            changeMenu.setVisibility(View.VISIBLE);
            changeMenu.setAnimation(popupShowAnim);

            switch(number) {
                case 0 :
                    if(tempSelectedCategoryMap.size() == 1) {
                        menuChangeMixScrollView.setVisibility(View.GONE);
                    } else {
                        loadCategoryMixList();
                        menuChangeMixScrollView.setVisibility(View.VISIBLE);
                    }
                    break;
                case 1 :
                    loadMenuSpotList(this, spotIcList, menuSpotList);
                    break;
                case 2 :
                    if(tempSelectedCategoryMap.size() > 1) {
                        menuChangeRemoveCategoryBtn.setText(getResources().getString(R.string.remove_without_many));
                        menuChangeRemoveCategoryNoteBtn.setText(getResources().getString(R.string.remove_with_many));
                    } else {
                        menuChangeRemoveCategoryBtn.setText(getResources().getString(R.string.remove_without));
                        menuChangeRemoveCategoryNoteBtn.setText(getResources().getString(R.string.remove_with));
                    }
                    break;
            }

            cacheMenuMod = number;
        }
    }

    public void closeHideMenu() {
        Log.d("secondLog", "closeHideMenu");
        clearAllSelectIcChangeModMenu(false);

        if(isHideMenuShow) {
            for (View v : changeModMenuContainersList)
                v.setVisibility(View.GONE);

            categoryNameEt.setEnabled(true);
            tagNameEt.setEnabled(true);
            menuChangeHideContainer.setVisibility(View.GONE);
            isHideMenuShow = false;
            cacheMenuMod = -1;
        }
    }

    private void closeCategoryChangeMod() {
        if(isMenuOpen) changeMenuBarAnim(false, true);
        else changeMenuBarAnim(false, false);

        changeMenuBtn.setText(getResources().getString(R.string.change));

        for (Map.Entry<Integer, Category> entry : tempSelectedCategoryMap.entrySet()) {
            LinearLayout categoryBox = findViewById(entry.getKey());
            if(categoryBox != null) switchCategoryIcSelect(categoryBox.findViewWithTag("selectIc"), false);
        }

        if(isMenuOpen) clearAllSelectIcChangeModMenu(true);
        else clearAllSelectIcChangeModMenu(false);

        if(tempSelectedCategoryMap.size() > 0) tempSelectedCategoryMap.clear();

        isMenuChangeModOn = false;
    }

    private void clearAllSelectIcChangeModMenu(boolean withAnimation){
        for (View v : changeModMenuBtnsList) {
            if(withAnimation) TransitionManager.beginDelayedTransition((ViewGroup)v.getParent(), new ChangeBounds().setDuration(150));

            v.findViewWithTag("ic").getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_IC_COLOR), PorterDuff.Mode.SRC_IN);

            LinearLayout icSelect = v.findViewWithTag("ic_select");

            RelativeLayout.LayoutParams icParams = (RelativeLayout.LayoutParams) icSelect.getLayoutParams();
            icParams.bottomMargin = -(int)dpToPixel(8);
            icSelect.setLayoutParams(icParams);
        }
    }

    private void loadCategoryMixList() {
        LinearLayout container = menuChangeSelectedCategoryContainer;
        container.removeAllViews();

        if(tempSelectedCategoryMap.size() > 1) {
            for(Map.Entry<Integer, Category> entry : tempSelectedCategoryMap.entrySet()) {
                String categoryName = entry.getValue().getName();

                //contentContainer
                LinearLayout contentContainer = new LinearLayout(this);
                LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(40));
                contentContainer.setLayoutParams(contentParams);
                contentContainer.setPadding(0,0,(int)dpToPixel(8), 0);
                contentContainer.setOrientation(LinearLayout.HORIZONTAL);

                //marginBox
                LinearLayout marginBox = new LinearLayout(this);
                LinearLayout.LayoutParams marginParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                marginParams.gravity = Gravity.CENTER_VERTICAL;
                marginParams.bottomMargin = (int)dpToPixel(1);
                marginBox.setLayoutParams(marginParams);

                //listIc
                ImageView listIc = new ImageView(this);
                ViewGroup.LayoutParams icParams = new ViewGroup.LayoutParams((int)dpToPixel(40), (int)dpToPixel(40));
                listIc.setLayoutParams(icParams);
                listIc.setBackground(getResources().getDrawable(R.drawable.ic_list_item));
                listIc.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_IC_COLOR), PorterDuff.Mode.SRC_IN);

                //nameView
                TextView nameView = new TextView(this);
                ViewGroup.LayoutParams nameParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                nameView.setLayoutParams(nameParams);
                nameView.setText(categoryName);
                nameView.setTextSize(13);
                nameView.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));

                //sizeCorrect
                if(screenYSize > screenXSize) {
                    if(categoryName.length() >= 22) nameView.setTextSize(11);
                    else nameView.setTextSize(13);
                }

                //implements
                contentContainer.addView(listIc);
                marginBox.addView(nameView);
                contentContainer.addView(marginBox);
                container.addView(contentContainer);
            }
        } else {
            //errorBox
            LinearLayout box = new LinearLayout(this);
            LinearLayout.LayoutParams boxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(40));
            box.setLayoutParams(boxParams);

            //marginBox
            LinearLayout marginBox = new LinearLayout(this);
            LinearLayout.LayoutParams marginParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            marginParams.gravity = Gravity.CENTER_VERTICAL;
            marginParams.leftMargin = (int)dpToPixel(14);
            marginBox.setLayoutParams(marginParams);

            //errorText
            TextView warningView = new TextView(this);
            warningView.setText(getResources().getString(R.string.not_enough_categories));
            warningView.setTextColor(getResources().getColor(R.color.error_color));
            warningView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            warningView.setTextSize(13);

            //implement
            marginBox.addView(warningView);
            box.addView(marginBox);
            container.addView(box);
        }
    }

    private void mixCategory() {
        if(tempSelectedCategoryMap.size() == 1) {
            for(Map.Entry<Integer, Category> entry : tempSelectedCategoryMap.entrySet()) {
                Category category = entry.getValue();
                String newName = menuChangeNewCategoryNameEt.getText().toString();

                if(mainPresenter.isValidateCategoryName(newName)) {
                    mainPresenter.updateCategory(category.getId(), newName, category.getSpot(), category.getNoteAmount());
                    mainPresenter.updateCategoryNameInNotes(category.getName(), newName);

                    category.setName(newName);
                    entry.setValue(category);

                    refreshMenuCategoryList("all", null);
//                    updateNoteList();
                    updateNoteList(true);
                    closeHideMenu();
                }
            }
        } else if(tempSelectedCategoryMap.size() > 1) {
            String newName = menuChangeNewCategoryNameEt.getText().toString();

            if(mainPresenter.isValidateCategoryName(newName)) {
                List<Category> categories = new ArrayList<>();

                for(Map.Entry<Integer, Category> entry : tempSelectedCategoryMap.entrySet())
                    categories.add(entry.getValue());

                mainPresenter.mixCategory(categories, newName);

                refreshMenuCategoryList("all", null);
//                updateNoteList();
                updateNoteList(true);

                closeHideMenu();
            }
        }
    }

    private void loadMenuSpotList(Context context, List<String> icList, LinearLayout container) {
        container.removeAllViews();

        int i = 0;
        int rowCount = 0;
        int rowWeight = spotRowBuilderMenu.getSpotRowWeight();
        int rowAmount = spotRowBuilderMenu.getRowAmount();
        int spotA = (int)spotRowBuilderMenu.getSpotA();

        List<LinearLayout> rowList = new ArrayList<>();

        for (int j = 0; j < rowAmount; j++) {
            LinearLayout row = new LinearLayout(context);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, spotA);
            row.setLayoutParams(rowParams);

            rowList.add(row);
        }

        for(String s : icList) {
            ImageView icBox = new ImageView(context);

            try {
                InputStream inputStream = getAssets().open(s);
                Bitmap bm = BitmapFactory.decodeStream(inputStream);
                icBox.setImageBitmap(bm);
                inputStream.close();
            } catch (IOException e) {}

            icBox.setLayoutParams(new ViewGroup.LayoutParams(spotA, spotA));
            icBox.setTag(s);

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String spotPath = v.getTag().toString();

                    if(spotPath.equalsIgnoreCase("spot_clean.png")) updateCategorySpot("");
                    else updateCategorySpot(spotPath);
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

    private void updateCategorySpot(String spotPath) {
        for(Map.Entry<Integer, Category> entry : tempSelectedCategoryMap.entrySet()) {
            Category c = entry.getValue();

            mainPresenter.updateCategorySpot(c, spotPath);

            c.setSpot(spotPath);
            entry.setValue(c);
        }

        refreshMenuCategoryList("all", null);
        closeHideMenu();
    }

    private void refreshMenuCategoryList(String searchStr, List<Category> categories) {
        allNotesAmount.setText(Integer.toString(countNotes()));
        if(searchStr.equalsIgnoreCase("all")) showCategory(mainPresenter.getAllCategory(), this, containerCategory);
        else showCategory(categories, this, containerCategory);
    }

    public void removeCategory(boolean withNote) {
        if(withNote) {
            for(Map.Entry<Integer, Category> entry : tempSelectedCategoryMap.entrySet()) {
                Category c = entry.getValue();

                List<Note> notes = mainPresenter.getNoteByCategory(c.getName());

                for(Note n : notes)
                    mainPresenter.removeNote(n.getId(), n.getCategory(), mainPresenter.getTagStr(n.getTags()));

                mainPresenter.removeCategory(c.getId(), c.getName());
            }
        } else {
            for(Map.Entry<Integer, Category> entry : tempSelectedCategoryMap.entrySet()) {
                Category c = entry.getValue();

                mainPresenter.clearCategoryInNote(c.getName());
                mainPresenter.removeCategory(c.getId(), c.getName());
            }
        }

        if(tempSelectedCategoryMap.size() > 1) showToast(ToastWarnings.CATEGORIES_REMOVED, null);
        else showToast(ToastWarnings.CATEGORY_REMOVED, null);

        /*currentCategory = getResources().getString(R.string.all_notes);
        titleNote.setText(currentCategory);*/
        setCurrentCategory(getResources().getString(R.string.all_notes));

        refreshMenuCategoryList("all", null);
        refreshTagList(0, null);
        updateNoteList();
        closeHideMenu();
    }

    private void refreshTagList(int type, List<Tag> tags) {
        if( type == 0) showTag(mainPresenter.getAllTag(), this, containerTag);
        else if (type == 1) showTag(tags, this, containerTag);
    }

    @Override
    public void runCategorySearch(LinearLayout container, List<Category> categories) {
        if(container.getId() == R.id.containerCategory) {
            closeCategoryChangeMod();
            if(!tagBtnSwither) showCategory(categories, this, container);
        } else {
            changeModLoadCategory(this, changeModCategoryList, categories);
        }
    }

    @Override
    public void runTagSearch(FlowLayout container, List<Tag> tags) {
        if(container.getId() == R.id.containerTag) refreshTagList(1, tags);
        else changeModLoadTags(this, container, tags);
    }

    @Override
    public void switchPopupContainer () {
        if(isChangeModOn) {
            if(tempSelectedMap.size() > 0) {
                RelativeLayout btnBox = (RelativeLayout) changeModBtnsList.get(3);
                AppCompatImageView ic = btnBox.findViewWithTag("ic");

                TransitionManager.beginDelayedTransition(popupNotificationContainer, new TransitionSet()
                        .setDuration(300)
                        .addTransition(new Fade()));

                Animation dropInAnimation = AnimationUtils.loadAnimation(this, R.anim.popup_show_anim_long);
                Animation dropOutAnimation = AnimationUtils.loadAnimation(this, R.anim.popup_hide_anim_long);

                if(!isPopupOpen) {
                    ic.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_1), PorterDuff.Mode.SRC_IN);

                    if(tempSelectedMap.size() > 1) popupRemoveBtnYes.setText(getResources().getString(R.string.popup_main_remove_notes_yes));
                    else popupRemoveBtnYes.setText(getResources().getString(R.string.popup_main_remove_note_yes));

                    tempCacheChangeMod = cacheChangeMod;
                    cacheChangeMod = 3;

                    popupNotificationContainer.setVisibility(View.VISIBLE);
                    popupRemoveBox.setAnimation(dropInAnimation);

                    isPopupOpen = true;
                } else {
                    ic.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_IC_COLOR), PorterDuff.Mode.SRC_IN);

                    cacheChangeMod = tempCacheChangeMod;
                    tempCacheChangeMod = -1;

                    popupRemoveBox.setAnimation(dropOutAnimation);
                    popupNotificationContainer.setVisibility(View.GONE);

                    isPopupOpen = false;
                }
            }
        }
    }

    private void removeNoteEvent() {
        if(isChangeModOn) {
            if(tempSelectedMap.size() > 0) {
                String entity = "Note";
                if(tempSelectedMap.size() > 1) entity = "Notes";

                for(Map.Entry<Integer, Note> pair : tempSelectedMap.entrySet()) {
                    Note note = pair.getValue();

                    mainPresenter.removeNote(note.getId(), note.getCategory(), mainPresenter.getTagStr(note.getTags()));
                }

                if(tempSelectedMap.size() > 1) showToast(ToastWarnings.NOTES_REMOVED, null);
                else showToast(ToastWarnings.NOTE_REMOVED, null);

                closeChangeMod();
                refreshMenuCategoryList("all", null);
                refreshTagList(0, null);

                updateNoteList();
            }
        }
    }

    private void clearTags() {
        if(isChangeModOn) {
            List<String> tagsNames = new ArrayList<>();

            for(Map.Entry<Integer, Note> entry : tempSelectedMap.entrySet()) {
                for(Tag t : entry.getValue().getTags()) {
                    String tagName = t.getName();
                    if(!tagsNames.contains(tagName) && !tagName.equalsIgnoreCase("")) tagsNames.add(tagName);
                }
            }

            if(tagsNames.size() > 0) {
                for(String s : tagsNames)
                    changeModRemoveTag(s, false);

                showToast(ToastWarnings.TAGS_REMOVED, null);
            }
        }
    }

    private void setCurrentCategory(String category) {
        currentCategory = category;
        titleNote.setText(category);

        if(screenYSize > screenXSize) {
            if(category.length() >= 18) titleNote.setTextSize(12);
            else titleNote.setTextSize(14);
        } else {
            if(category.length() >= 32) titleNote.setTextSize(11);
            else titleNote.setTextSize(14);
        }
    }

    private void noteBtnAnim(boolean toBar) {
        LinearLayout.LayoutParams btnParams = (LinearLayout.LayoutParams) addNoteBtnContainer.getLayoutParams();

        TransitionManager.beginDelayedTransition(addNoteBtnContainer, new ChangeBounds()
                .setDuration(250)
                .setInterpolator(new AccelerateDecelerateInterpolator()));

        if(toBar) {
            btnParams.topMargin = 5;
            addNoteBtnContainer.setLayoutParams(btnParams);
        } else {
            btnParams.topMargin = -(int)dpToPixel(55);
            addNoteBtnContainer.setLayoutParams(btnParams);
        }
    }

    private void changeModOpenAnim(boolean toOpen) {
        if(!isOpenAnim) {
            isOpenAnim = true;

            TransitionManager.beginDelayedTransition(changeModAnimContainer, new ChangeBounds()
                    .setDuration(400)
                    .setInterpolator(new AccelerateDecelerateInterpolator()));

            TransitionManager.beginDelayedTransition(closeChangeModZone, new TransitionSet()
                    .setDuration(200)
                    .addTransition(new Fade())
                    .addTransition(new Scale(0.1f)));

            changeModOpenSwitcher(toOpen);
        }
    }

    private void changeModOpenSwitcher(boolean toOpen) {
        if(toOpen) {
            closeChangeModZone.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams containerParams = (RelativeLayout.LayoutParams) changeModAnimContainer.getLayoutParams();
            containerParams.bottomMargin = 0;
            changeModAnimContainer.setLayoutParams(containerParams);
        } else {
            closeChangeModZone.setVisibility(View.GONE);
            RelativeLayout.LayoutParams containerParams = (RelativeLayout.LayoutParams) changeModAnimContainer.getLayoutParams();
            containerParams.bottomMargin = (int)(-changeMenuYSizeFloat);
            changeModAnimContainer.setLayoutParams(containerParams);

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

    private void switchCategoryIcSelect(View icSelect, boolean toOpen) {
        if(toOpen) {
            icSelect.setVisibility(View.VISIBLE);

            Animation showAnim = AnimationUtils.loadAnimation(this, R.anim.select_item_in_anim);
            icSelect.setAnimation(showAnim);
        } else {
            Animation hideAnim = AnimationUtils.loadAnimation(this, R.anim.select_item_out_anim);
            icSelect.setAnimation(hideAnim);

            icSelect.setVisibility(View.GONE);
        }
    }

    private void changeMenuBarAnim (boolean toOpen, boolean withAnim) {
        //menuBarChangeContainer
        LinearLayout.LayoutParams btnParams = (LinearLayout.LayoutParams) menuBarChangeContainer.getLayoutParams();
        int margin;

        if(withAnim)
            TransitionManager.beginDelayedTransition(menuBarChangeContainer, new ChangeBounds()
                    .setDuration(300)
                    .setInterpolator(new AccelerateDecelerateInterpolator()));

        if(toOpen) {
            margin = -(int)dpToPixel(55);
        } else {
            margin = 0;
        }

        btnParams.setMargins(0, margin, 0, 0);
        menuBarChangeContainer.setLayoutParams(btnParams);
    }

    @Override
    public void changeModTagAnimation(LinearLayout view, boolean isTo) {
        if(isTo) {
            Animation inAnimation = AnimationUtils.loadAnimation(this, R.anim.opacity_in_anim);
            view.startAnimation(inAnimation);
        } else {
            Animation outAnimation = AnimationUtils.loadAnimation(this, R.anim.opacity_out_anim);
            view.startAnimation(outAnimation);
        }
    }

    private void switchThemeScreen(boolean withAnimation) {
        refreshTheme();

        if(!isThemeScreenOpen) {
            //products list
            loadThemes();
            loadCollections();

            Map <Collection, List<Theme>> productsMap = new HashMap<>();
            Map<String, List<Theme>> tempMap = new HashMap<>();

            for(Map.Entry<String, Theme> entry : themes.entrySet()){
                Theme theme = entry.getValue();
                String collectionId = theme.getCollectionId();

                if(!tempMap.containsKey(collectionId)) tempMap.put(collectionId, new ArrayList<Theme>());
                tempMap.get(collectionId).add(theme);
            }

            for(Map.Entry<String, List<Theme>> entry : tempMap.entrySet()) {
                Collection collection = collections.get(entry.getKey());
                List<Theme> value = entry.getValue();

                if(collection.isPurchased()) {
                    for(Theme t : value)
                        t.setPurchased(true);
                }

                productsMap.put(collection, value);
            }

            switchThemeScreenBuilder(productsMap);

            checkProducts();

            if(generalAttention.checkGift()) giftEvent();

            //current theme
            List<Theme> tempCurrentThemeList = new ArrayList<>();
            tempCurrentThemeList.add(new Theme("current", "", "", "","0", themes.get(Themes.DEFAULT).getColorRow(), mapColor, true));
            loadThemeList(themeCurrentContainer, tempCurrentThemeList, false, true);

            //open animation
            if(withAnimation) {
                TransitionManager.beginDelayedTransition(themeMainContainer, new Slide(Gravity.BOTTOM)
                        .setDuration(300)
                        .setInterpolator(new AccelerateDecelerateInterpolator()));
            }

            themeMainContainer.setVisibility(View.VISIBLE);

            //toplineDiscount title
            if(generalAttention.checkOfferSale()) {
                if(generalAttention.getOfferType().equals(generalAttention.OFFER_FIRST)) {
                    themeToplineTitle.setText(R.string.hours_sale);
                    themeToplineSaleIc.setVisibility(View.VISIBLE);
                } else if(generalAttention.getOfferType().equals(generalAttention.OFFER_20_NOTES)) {
                    themeToplineTitle.setText(R.string.discount);
                    themeToplineSaleIc.setVisibility(View.VISIBLE);
                }
            } else {
                themeToplineTitle.setText(R.string.theme);
                themeToplineSaleIc.setVisibility(View.GONE);
            }

            isThemeScreenOpen = true;
        } else {
            //close animation
            if(withAnimation) {
                TransitionManager.beginDelayedTransition(themeMainContainer, new Slide(Gravity.BOTTOM)
                        .setDuration(300)
                        .setInterpolator(new AccelerateDecelerateInterpolator()));
            }

            themeMainContainer.setVisibility(View.GONE);

            isThemeScreenOpen = false;
        }
    }

    private void switchThemeScreenBuilder(Map <Collection, List<Theme>> productsMap) {
        //load themes miniature
        //clear mainList
        themeCollectionsList.removeAllViews();

        if(tempThemeMap.size() > 0) tempThemeMap.clear();
        if(tempCollectionMap.size() > 0) tempCollectionMap.clear();
        if(tempCollectionsLines.size() > 0) tempCollectionsLines.clear();

        int dp30 = (int)dpToPixel(30);
        int dp20 = (int)dpToPixel(20);
        int dp10 = (int)dpToPixel(10);

        for (Map.Entry<Collection, List<Theme>> entry : productsMap.entrySet()) {
            Collection key = entry.getKey();
            List<Theme> value = entry.getValue();

            if(key.getId().equals(Collections.DEFAULT_ID)) {
                loadThemeList((LinearLayout) themeDefaultContainer.findViewWithTag("list_container"), entry.getValue(), true, false);
            } else {
                //CREATE DROP LIST
                //container
                LinearLayout container = new LinearLayout(context);
                LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                container.setLayoutParams(containerParams);
                container.setOrientation(LinearLayout.VERTICAL);
                //titleView
                LinearLayout titleView = new LinearLayout(context);
                LinearLayout.LayoutParams titleViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(36));
                titleView.setLayoutParams(titleViewParams);
                titleView.setPadding(dp20, 0, dp20, 0);
                titleView.setTag("title");
                titleView.setOrientation(LinearLayout.HORIZONTAL);
                //icBox "ic"
                LinearLayout icBox = new LinearLayout(context);
                LinearLayout.LayoutParams icBoxParams = new LinearLayout.LayoutParams(dp30, dp30);
                icBoxParams.gravity = Gravity.CENTER_VERTICAL;
                icBox.setLayoutParams(icBoxParams);
                icBox.setTag("ic");
                icBox.setRotation(180);
                //ic
                ImageView ic = new ImageView(context);
                LinearLayout.LayoutParams icParams = new LinearLayout.LayoutParams(dp30, dp30);
                ic.setLayoutParams(icParams);
                ic.setBackground(getResources().getDrawable(R.drawable.ic_angle_down));
                ic.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_DARK), PorterDuff.Mode.SRC_IN);
                //title
                TextView title = new TextView(context);
                LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                titleParams.gravity = Gravity.CENTER_VERTICAL;
                title.setLayoutParams(titleParams);
                title.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));
                title.setTextSize(14);
                title.setPadding(dp10, 0, 0, 0);
                title.setText(entry.getKey().getShortName());
                //titleLine
                LinearLayout titleLine = new LinearLayout(context);
                LinearLayout.LayoutParams titleLineParams = new LinearLayout.LayoutParams(0, (int)dpToPixel(1), 1f);
                titleLineParams.leftMargin = dp10;
                titleLineParams.gravity = Gravity.CENTER_VERTICAL;
                titleLine.setLayoutParams(titleLineParams);
                titleLine.setBackgroundColor(mapColor.get(ThemeConstants.BACK_DARK_LIGHT));
                //mainList "list"
                RelativeLayout mainList = new RelativeLayout(context);
                LinearLayout.LayoutParams mainListParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(140));
                mainList.setLayoutParams(mainListParams);
                mainList.setTag("list");
                //horizontalScroll
                HorizontalScrollView horizontalScroll = new HorizontalScrollView(context);
                RelativeLayout.LayoutParams horizontalScrollParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                horizontalScroll.setLayoutParams(horizontalScrollParams);
                horizontalScroll.setHorizontalScrollBarEnabled(false);
                //miniaturesList "list_container"
                LinearLayout miniaturesList = new LinearLayout(context);
                ViewGroup.LayoutParams miniaturesListParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                miniaturesList.setLayoutParams(miniaturesListParams);
                miniaturesList.setPadding(dp20, 0, dp20, 0);
                miniaturesList.setOrientation(LinearLayout.VERTICAL);
                miniaturesList.setTag("list_container");
                //hideZone1
                ImageView hideZone1 = new ImageView(context);
                RelativeLayout.LayoutParams hideZoneLeftParams = new RelativeLayout.LayoutParams(dp20, ViewGroup.LayoutParams.MATCH_PARENT);
                hideZoneLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                hideZone1.setLayoutParams(hideZoneLeftParams);
                hideZone1.setBackground(getResources().getDrawable(R.drawable.hide_zone_left));
                hideZone1.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_3), PorterDuff.Mode.SRC_IN);
                //hideZone2
                ImageView hideZone2 = new ImageView(context);
                RelativeLayout.LayoutParams hideZoneRightParams = new RelativeLayout.LayoutParams(dp20, ViewGroup.LayoutParams.MATCH_PARENT);
                hideZoneRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                hideZone2.setLayoutParams(hideZoneRightParams);
                hideZone2.setBackground(getResources().getDrawable(R.drawable.hide_zone_right));
                hideZone2.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_3), PorterDuff.Mode.SRC_IN);
                //drop list implement
                //title
                icBox.addView(ic);
                titleView.addView(icBox);
                titleView.addView(title);
                titleView.addView(titleLine);
                container.addView(titleView);
                //list
                horizontalScroll.addView(miniaturesList);
                mainList.addView(horizontalScroll);
                mainList.addView(hideZone1);
                mainList.addView(hideZone2);
                container.addView(mainList);
                //end
                themeCollectionsList.addView(container);

                //onClick
                View.OnClickListener titleClick = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dropThemeList(view);
                    }
                };
                titleView.setOnClickListener(titleClick);

                //loadMiniatures
                loadThemeList(miniaturesList, entry.getValue(), true, false);

                //CREATE COLLECTION LINE
                if(!key.isPurchased()){
                    //box
                    LinearLayout collectionBox = new LinearLayout(context);
                    LinearLayout.LayoutParams collectionBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    collectionBox.setLayoutParams(collectionBoxParams);
                    collectionBox.setPadding(dp10, 0, 0, dp20);
                    collectionBox.setOrientation(LinearLayout.HORIZONTAL);
                    //btn
                    Button collectionBtn = new Button(context);
                    LinearLayout.LayoutParams collectionBtnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    collectionBtnParams.setMargins(dp10, dp20, 0, dp20);
                    collectionBtn.setLayoutParams(collectionBtnParams);
                    collectionBtn.setPadding(dp20, 0, dp20, 0);
                    collectionBtn.setBackground(getResources().getDrawable(R.drawable.soft_ac_btn));
                    collectionBtn.getBackground().setColorFilter(mapColor.get(ThemeConstants.AC_1), PorterDuff.Mode.SRC_IN);
                    collectionBtn.setElevation((int)dpToPixel(2));
                    collectionBtn.setTranslationZ((int)dpToPixel(4));
                    collectionBtn.setText(getResources().getText(R.string.collection));
                    collectionBtn.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));

//                    collectionBtn.setTag(String.format("%d", iCollection));
                    /*int btnId = View.generateViewId();
                    collectionBtn.setId(btnId);
                    tempCollectionMap.put(btnId, key);*/

                    collectionBtn.setTag(key.getId());
                    tempCollectionsLines.put(key.getId(), collectionBox);

                    //icon
                    ImageView collectionIc = new ImageView(context);
                    LinearLayout.LayoutParams collectionIcParams = new LinearLayout.LayoutParams((int)dpToPixel(36), (int)dpToPixel(36));
                    collectionIcParams.gravity = Gravity.CENTER_VERTICAL;
                    collectionIcParams.leftMargin = (int)dpToPixel(16);
                    collectionIc.setLayoutParams(collectionIcParams);
                    collectionIc.setBackground(getResources().getDrawable(R.drawable.ic_purchase_success));
                    //text
                    TextView collectionText = new TextView(context);
                    LinearLayout.LayoutParams collectionTextParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    collectionTextParams.gravity = Gravity.CENTER_VERTICAL;
                    collectionTextParams.leftMargin = dp10;
                    collectionText.setLayoutParams(collectionTextParams);
                    collectionText.setText(getResources().getText(R.string.profit_50));
                    collectionText.setAllCaps(true);
                    collectionText.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));

                    //onClick
                    View.OnClickListener collectionBtnListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickOnProduct(view.getTag().toString());
                        }
                    };
                    collectionBtn.setOnClickListener(collectionBtnListener);

                    //collection implement
                    collectionBox.addView(collectionBtn);
                    collectionBox.addView(collectionIc);
                    collectionBox.addView(collectionText);

                    themeCollectionsList.addView(collectionBox);
                }

//                iCollection++;
            }
        }
    }

    private void loadThemeList(LinearLayout container, List<Theme> themesList, boolean withClick, boolean isSmall) {
        List<View> miniatures = new ArrayList<>();

        int a;

        if(!isSmall) {
            if(screenYSize > screenXSize) a = (menuXSize-(int)dpToPixel(40))/3;
            else a = (menuXSize-(int)dpToPixel(40))/5;
        } else {
            if(screenYSize > screenXSize) a = menuXSize/100*25;
            else a = menuXSize/100*15;
        }

        container.removeAllViews();

        if(themesList.size() > 1){
            View tempParent = (View)container.getParent();
            RelativeLayout parentContainer = (RelativeLayout) tempParent.getParent();
            LinearLayout.LayoutParams parentParams = (LinearLayout.LayoutParams) parentContainer.getLayoutParams();

            if(themesList.size() > 3) parentParams.height = a*2;
            else parentParams.height = a;

//            parentParams.height += (int)dpToPixel(40);

            parentContainer.setLayoutParams(parentParams);
        }

        for (Theme t : themesList) {
            Map<String, Integer> themeColorMap = t.getColorMap();

            //box
            RelativeLayout box = new RelativeLayout(this);
            RelativeLayout.LayoutParams boxParams = new RelativeLayout.LayoutParams(a, a);
            box.setLayoutParams(boxParams);

            //contentMarginBox
            int dp6 = (int)dpToPixel(6);
            RelativeLayout contentMarginBox = new RelativeLayout(this);
            RelativeLayout.LayoutParams contentMarginParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            contentMarginParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            contentMarginParams.addRule(RelativeLayout.CENTER_VERTICAL);
            contentMarginBox.setLayoutParams(contentMarginParams);
            contentMarginBox.setPadding(dp6, dp6, dp6, dp6);

            //contentBox
            LinearLayout contentBox = new LinearLayout(this);
            LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            contentBox.setLayoutParams(contentParams);
            contentBox.setBackground(getResources().getDrawable(R.drawable.popup_white_shape));
            contentBox.getBackground().setColorFilter(themeColorMap.get(ThemeConstants.BACK_1), PorterDuff.Mode.SRC_IN);
            contentBox.setOrientation(LinearLayout.VERTICAL);

            //boxLine1
            LinearLayout boxLine1 = new LinearLayout(this);
            LinearLayout.LayoutParams boxLineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.2f);
            boxLine1.setLayoutParams(boxLineParams);
            boxLine1.setOrientation(LinearLayout.HORIZONTAL);
                //circleBox
            RelativeLayout circleBox = new RelativeLayout(this);
            LinearLayout.LayoutParams circleBoxParams = new LinearLayout.LayoutParams(0, RelativeLayout.LayoutParams.MATCH_PARENT, 0.35f);
            circleBox.setLayoutParams(circleBoxParams);
                    //circleView
            LinearLayout circleView = new LinearLayout(this);
            RelativeLayout.LayoutParams circleParams = new RelativeLayout.LayoutParams((int)dpToPixel(9), (int)dpToPixel(9));
            circleParams.addRule(RelativeLayout.CENTER_VERTICAL);
            circleParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            circleView.setLayoutParams(circleParams);
            circleView.setBackground(getResources().getDrawable(R.drawable.circle_shape));
            circleView.getBackground().setColorFilter(themeColorMap.get(ThemeConstants.BACK_DARK_LIGHT), PorterDuff.Mode.SRC_IN);
                //textMockBox
            RelativeLayout textMockBox = new RelativeLayout(this);
            LinearLayout.LayoutParams textMockBoxParams = new LinearLayout.LayoutParams(0, RelativeLayout.LayoutParams.MATCH_PARENT, 0.65f);
            textMockBox.setLayoutParams(textMockBoxParams);
                    //textMock
            LinearLayout textMock = new LinearLayout(this);
            RelativeLayout.LayoutParams textMockParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(5));
            textMockParams.addRule(RelativeLayout.CENTER_VERTICAL);
            textMockParams.rightMargin = (int)dpToPixel(6);
            textMock.setLayoutParams(textMockParams);
            textMock.setBackground(getResources().getDrawable(R.drawable.tag_shape));
            textMock.getBackground().setColorFilter(themeColorMap.get(ThemeConstants.TEXT_LIGHT), PorterDuff.Mode.SRC_IN);
            //boxLine2
            LinearLayout boxLine2 = new LinearLayout(this);
            boxLine2.setLayoutParams(boxLineParams);
            boxLine2.setOrientation(LinearLayout.HORIZONTAL);
                //circleBox
            RelativeLayout circleBox2 = new RelativeLayout(this);
            circleBox2.setLayoutParams(circleBoxParams);
                    //circleView
            LinearLayout circleView2 = new LinearLayout(this);
            circleView2.setLayoutParams(circleParams);
            circleView2.setBackground(getResources().getDrawable(R.drawable.circle_shape));
            circleView2.getBackground().setColorFilter(themeColorMap.get(ThemeConstants.BACK_DARK_LIGHT), PorterDuff.Mode.SRC_IN);
                //textMockBox
            RelativeLayout textMockBox2 = new RelativeLayout(this);
            textMockBox2.setLayoutParams(textMockBoxParams);
                    //textMock
            LinearLayout textMock2 = new LinearLayout(this);
            RelativeLayout.LayoutParams textMockParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(5));
            textMockParams2.addRule(RelativeLayout.CENTER_VERTICAL);
            textMockParams2.rightMargin = (int)dpToPixel(20);
            textMock2.setLayoutParams(textMockParams2);
            textMock2.setBackground(getResources().getDrawable(R.drawable.tag_shape));
            textMock2.getBackground().setColorFilter(themeColorMap.get(ThemeConstants.TEXT_LIGHT), PorterDuff.Mode.SRC_IN);
            //boxLine3
            LinearLayout boxLine3 = new LinearLayout(this);
            boxLine3.setLayoutParams(boxLineParams);
            boxLine3.setOrientation(LinearLayout.HORIZONTAL);
                //circleBox
            RelativeLayout circleBox3 = new RelativeLayout(this);
            circleBox3.setLayoutParams(circleBoxParams);
                    //circleView
            LinearLayout circleView3 = new LinearLayout(this);
            circleView3.setLayoutParams(circleParams);
            circleView3.setBackground(getResources().getDrawable(R.drawable.circle_shape));
            circleView3.getBackground().setColorFilter(themeColorMap.get(ThemeConstants.BACK_DARK_LIGHT), PorterDuff.Mode.SRC_IN);
                //textMockBox
            RelativeLayout textMockBox3 = new RelativeLayout(this);
            textMockBox3.setLayoutParams(textMockBoxParams);
                    //textMock
            LinearLayout textMock3 = new LinearLayout(this);
            RelativeLayout.LayoutParams textMockParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(5));
            textMockParams3.addRule(RelativeLayout.CENTER_VERTICAL);
            textMockParams3.rightMargin = (int)dpToPixel(12);
            textMock3.setLayoutParams(textMockParams3);
            textMock3.setBackground(getResources().getDrawable(R.drawable.tag_shape));
            textMock3.getBackground().setColorFilter(themeColorMap.get(ThemeConstants.TEXT_LIGHT), PorterDuff.Mode.SRC_IN);

            //underLine
            LinearLayout underline = new LinearLayout(this);
            LinearLayout.LayoutParams underlineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(4));
            underline.setLayoutParams(underlineParams);
            underline.setBackgroundColor(themeColorMap.get(ThemeConstants.AC_2));

            //acBox
            RelativeLayout acBox = new RelativeLayout(this);
            LinearLayout.LayoutParams acBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.3f);
            acBox.setLayoutParams(acBoxParams);
            acBox.setBackground(getResources().getDrawable(R.drawable.bottom_rounded_shape));
            acBox.getBackground().setColorFilter(themeColorMap.get(ThemeConstants.AC_1), PorterDuff.Mode.SRC_IN);
                //previewIc
            LinearLayout previewIc = new LinearLayout(this);
            RelativeLayout.LayoutParams previewIcParams = new RelativeLayout.LayoutParams((int)dpToPixel(16), (int)dpToPixel(8));
            previewIcParams.addRule(RelativeLayout.CENTER_VERTICAL);
            previewIcParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            previewIc.setLayoutParams(previewIcParams);
            previewIc.setBackground(getResources().getDrawable(R.drawable.circle_shape));
            previewIc.getBackground().setColorFilter(themeColorMap.get(ThemeConstants.TEXT_LIGHT), PorterDuff.Mode.SRC_IN);

            //marginBox1
            LinearLayout marginBox1 = new LinearLayout(this);
            LinearLayout.LayoutParams marginBox1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(4));
            marginBox1.setLayoutParams(marginBox1Params);
            //marginBox1
            LinearLayout marginBox2 = new LinearLayout(this);
            LinearLayout.LayoutParams marginBox2Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)dpToPixel(2));
            marginBox2.setLayoutParams(marginBox2Params);

            //implement
                //boxLine
            circleBox.addView(circleView);
            circleBox2.addView(circleView2);
            circleBox3.addView(circleView3);
            textMockBox.addView(textMock);
            textMockBox2.addView(textMock2);
            textMockBox3.addView(textMock3);
            boxLine1.addView(circleBox);
            boxLine1.addView(textMockBox);
            boxLine2.addView(circleBox2);
            boxLine2.addView(textMockBox2);
            boxLine3.addView(circleBox3);
            boxLine3.addView(textMockBox3);
                    //boxLine to Content
            contentBox.addView(marginBox1);
            contentBox.addView(boxLine1);
            contentBox.addView(boxLine2);
            contentBox.addView(boxLine3);
            contentBox.addView(marginBox2);
                //underline
            contentBox.addView(underline);
                //acBox
            if(!isSmall) acBox.addView(previewIc);
            contentBox.addView(acBox);
                //contentBox
            contentMarginBox.addView(contentBox);
            box.addView(contentMarginBox);

            //onClick
            /*int id = View.generateViewId();
            box.setId(id);
            tempThemeMap.put(id, t);*/
            box.setTag(t.getId());

            if(withClick) {
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickOnProduct(view.getTag().toString());
                    }
                };

                box.setOnClickListener(clickListener);
            }

                //addToList
            miniatures.add(box);
        }

        boolean isTwo = false;
        int count = 0;

        LinearLayout listView = new LinearLayout(this);
        LinearLayout.LayoutParams listVeiwParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, a);
        listView.setLayoutParams(listVeiwParams);

        LinearLayout listView2 = new LinearLayout(this);
        listView2.setLayoutParams(listVeiwParams);

        if(themes.size() > 3) isTwo = true;

        for(View v : miniatures) {
            if(!isTwo) {
                listView.addView(v);
            } else {
                if(count >= 0) listView.addView(v);
                else listView2.addView(v);

                count++;
                if(count == 3) count = -4;
                if(count == -1) count = 0;
            }
        }

        container.addView(listView);
        if(isTwo) container.addView(listView2);
    }

    private void clickOnProduct(String id) {
        try {
            if(id.substring(0, 5).equalsIgnoreCase("theme")) {
                tempTheme = themes.get(id);
                isBuyCardTheme = true;
                openThemePreview(tempTheme.getColorMap(), tempTheme.isPurchased());
            } else if(id.substring(0, 10).equalsIgnoreCase("collection")) {
                tempCollection = collections.get(id);
                isBuyCardTheme = false;
                switchThemeBuyCard(false);
            }
        } catch (StringIndexOutOfBoundsException e) {
            Log.d(SECOND_LOG, "!!!ERROR StringIndexOutOfBoundsException");
            Toast.makeText(context, "Error: StringIndexOutOfBoundsException", Toast.LENGTH_LONG).show();
        }
    }

    private void dropThemeList(View v) {
        View parent = (View) v.getParent();
        RelativeLayout list = (RelativeLayout) parent.findViewWithTag("list");
        LinearLayout ic = parent.findViewWithTag("ic");

        TransitionManager.beginDelayedTransition(ic, new Rotate()
                    .setDuration(150)
                    .setInterpolator(new AccelerateDecelerateInterpolator()));

        if(list.getVisibility() == View.GONE) {
            ic.setRotation(180f);
            list.setVisibility(View.VISIBLE);
        } else {
            ic.setRotation(0f);
            list.setVisibility(View.GONE);
        }
    }

    private void openThemePreview(Map<String, Integer> themeColorMap, boolean isPurchased){
        if(!isThemePreviewOpen) {
            RelativeLayout.LayoutParams buyBtnParams = (RelativeLayout.LayoutParams) themeBuyBtn.getLayoutParams();

            if(isPurchased) {
                themeBuyBtnText.setText(getResources().getText(R.string.set_theme));
                themeBuyBtnIc.setBackground(getResources().getDrawable(R.drawable.ic_theme));
                themeBuyBtnIc.getBackground().clearColorFilter();

                buyBtnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                buyBtnParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                buyBtnParams.rightMargin = 0;

                themeBuyBtnText.setTextSize(12);

                themeBuyBtn.setTag("set");
            } else {
                themeBuyBtnText.setText(getResources().getText(R.string.buy));
                themeBuyBtnIc.setBackground(getResources().getDrawable(R.drawable.ic_buy));
                themeBuyBtnIc.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_DARK), PorterDuff.Mode.SRC_IN);

                buyBtnParams.removeRule(RelativeLayout.CENTER_HORIZONTAL);
                buyBtnParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                buyBtnParams.rightMargin = themeBuyBtnRightMargin;

                themeBuyBtnText.setTextSize(18);

                themeBuyBtn.setTag("buy");
            }

            themeBuyBtn.setLayoutParams(buyBtnParams);

            themePreviewNavMatrix[0] = 1;
            themePreviewNavMatrix[1] = 0;
            themePreviewNavMatrix[2] = 0;

            for (View v : themePreviewNavItemsList)
                v.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_IC_COLOR), PorterDuff.Mode.SRC_IN);

            themePreviewNavItemsList.get(0).getBackground().setColorFilter(mapColor.get(ThemeConstants.AC_1), PorterDuff.Mode.SRC_IN);

            RelativeLayout.LayoutParams activeParams = new RelativeLayout.LayoutParams(menuXSize, ViewGroup.LayoutParams.MATCH_PARENT);
            activeParams.leftMargin = 0;
            activeParams.rightMargin = 0;
            RelativeLayout.LayoutParams hideParams = new RelativeLayout.LayoutParams(menuXSize, ViewGroup.LayoutParams.MATCH_PARENT);
            hideParams.leftMargin = menuXSize;
            hideParams.rightMargin = 0;

            themePreviewScreenMatrix.get(0).setLayoutParams(activeParams);
            themePreviewScreenMatrix.get(1).setLayoutParams(hideParams);
            themePreviewScreenMatrix.get(2).setLayoutParams(hideParams);

            ThemePreviewBuilder themePreviewBuilder = new ThemePreviewBuilder(this, themePreview1, themePreview2, themePreview3, themeColorMap);
            themePreviewBuilder.buildScreen1();
            themePreviewBuilder.buildScreen2();
            themePreviewBuilder.buildScreen3();

            Animation inFadeAnimation = AnimationUtils.loadAnimation(this, R.anim.opacity_in_anim_long);
            Animation inDropAnimation = AnimationUtils.loadAnimation(this, R.anim.popup_show_anim_long);

            themePreviewBlur.setVisibility(View.VISIBLE);
            themePreviewScreen.setVisibility(View.VISIBLE);

            themePreviewBlur.setAnimation(inFadeAnimation);
            themePreviewScreen.setAnimation(inDropAnimation);

            isThemePreviewOpen = !isThemePreviewOpen;
        }
    }

    private void closeThemePreview(){
        if(isThemePreviewOpen) {
            Animation outFadeAnimation = AnimationUtils.loadAnimation(this, R.anim.opacity_out_anim_long);
            Animation outDropAnimation = AnimationUtils.loadAnimation(this, R.anim.popup_hide_anim_long);

            themePreviewBlur.setAnimation(outFadeAnimation);
            themePreviewScreen.setAnimation(outDropAnimation);

            createThemeWrapsList();
            ThemeSetter tempThemeSetter = new ThemeSetter(themeWrapsList, mapColor);
            buildTheme(tempThemeSetter);

            refreshTheme();

            themePreviewBlur.setVisibility(View.GONE);
            themePreviewScreen.setVisibility(View.GONE);

            isThemePreviewOpen = !isThemePreviewOpen;
        }
    }

    private void clickToBuyBtn() {
        if(themeBuyBtn.getTag().equals("buy")) switchThemeBuyCard(true);
        else setTheme();
    }

    private void switchThemeBuyCard(boolean isTheme) {
        if(!isThemeBuyCardOpen) {
            Animation inFadeAnimation = AnimationUtils.loadAnimation(this, R.anim.opacity_in_anim_long);
            Animation inDropAnimation = AnimationUtils.loadAnimation(this, R.anim.popup_show_anim_long);

            if (isTheme) {
                //to theme
                themeBuyCardThemeTitle.setText(tempTheme.getName());
                setBuyCardPrice(true);

                List<Theme> mockThemeList = new ArrayList<>();
                mockThemeList.add(tempTheme);

                loadThemeList(themeBuyCardMiniatureContainer, mockThemeList, false, false);
            } else {
                //to collection
                loadCollectionCardInfo();
            }

            themeBuyCardBlur.setVisibility(View.VISIBLE);
            themeBuyCardContainer.setVisibility(View.VISIBLE);

            themeBuyCardBlur.setAnimation(inFadeAnimation);
            themeBuyCardContainer.setAnimation(inDropAnimation);
        } else {
            Animation outFadeAnimation = AnimationUtils.loadAnimation(this, R.anim.opacity_out_anim_long);
            Animation outDropAnimation = AnimationUtils.loadAnimation(this, R.anim.popup_hide_anim_long);

            isThemeScreenOpen = false;
            switchThemeScreen(false);

            themeBuyCardBlur.setAnimation(outFadeAnimation);
            themeBuyCardContainer.setAnimation(outDropAnimation);

            themeBuyCardBlur.setVisibility(View.GONE);
            themeBuyCardContainer.setVisibility(View.GONE);
        }

        isThemeBuyCardOpen = !isThemeBuyCardOpen;
    }

    private void loadCollectionCardInfo() {
        themeBuyCardMiniatureContainer.removeAllViews();

        themeBuyCardThemeTitle.setText(tempCollection.getName());
        setBuyCardPrice(false);

        //container
        LinearLayout container = new LinearLayout(context);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        containerParams.leftMargin = (int)dpToPixel(10);
        containerParams.rightMargin = (int)dpToPixel(10);
        container.setLayoutParams(containerParams);
        container.setOrientation(LinearLayout.HORIZONTAL);

        //miniature
        RelativeLayout miniatureBox = new RelativeLayout(context);
        LinearLayout.LayoutParams miniatureBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
        miniatureBox.setLayoutParams(miniatureBoxParams);
            //miniatureImg
        ImageView miniatureImg = new ImageView(context);
        RelativeLayout.LayoutParams miniatureImgParams = new RelativeLayout.LayoutParams((int)dpToPixel(100), (int)dpToPixel(75));
        miniatureImgParams.addRule(RelativeLayout.CENTER_VERTICAL);
        miniatureImgParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        miniatureImg.setLayoutParams(miniatureImgParams);

        switch(tempCollection.getId()) {
            case "collection_common" :
                miniatureImg.setBackground(getResources().getDrawable(R.drawable.collection_common));
                break;
            case "collection_dark" :
                miniatureImg.setBackground(getResources().getDrawable(R.drawable.collection_dark));
                break;
            case "collection_juicy" :
                miniatureImg.setBackground(getResources().getDrawable(R.drawable.collection_juicy));
                break;
            case "collection_minimalistic" :
                miniatureImg.setBackground(getResources().getDrawable(R.drawable.collection_minimalistic));
                break;
            default:
                miniatureImg.setBackground(getResources().getDrawable(R.drawable.collection_common));
                break;
        }

        //text
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
        textViewParams.gravity = Gravity.CENTER_VERTICAL;
        textViewParams.bottomMargin = (int)dpToPixel(10);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(12);
        textView.setTextColor(mapColor.get(ThemeConstants.TEXT_LIGHT));
        textView.setText(R.string.collection_offer);

        //implement
        miniatureBox.addView(miniatureImg);
        container.addView(miniatureBox);
        container.addView(textView);
        themeBuyCardMiniatureContainer.addView(container);
    }

    private void setBuyCardPrice(boolean isTheme) {
        String price = "";
        String oldPrice = "";

        if(isTheme) price = tempTheme.getPrice();
        else price = tempCollection.getPrice();

        themeBuyCardPrice.setText(price);

        if(generalAttention.checkOfferSale()) {
            themeBuyCardSaleIc.setVisibility(View.VISIBLE);
            themeBuyCardSalePriceBox.setVisibility(View.VISIBLE);

            try {
                if(isTheme) oldPrice = tempProductsPriceMap.get(tempTheme.getId())[0];
                else oldPrice = tempProductsPriceMap.get(tempCollection.getId())[0];

                themeBuyCardSalePrice.setText(oldPrice);
            } catch (NullPointerException e) {
                themeBuyCardSalePriceBox.setVisibility(View.GONE);
            }
        } else {
            themeBuyCardSalePriceBox.setVisibility(View.GONE);
            themeBuyCardSaleIc.setVisibility(View.GONE);
        }

        if(isTheme) Log.d(SECOND_LOG, String.format("tempTheme id: %s, price: %s", tempTheme.getId(), tempTheme.getPrice()));
        else Log.d(SECOND_LOG, String.format("tempCollection id: %s, price: %s", tempCollection.getId(), tempCollection.getPrice()));
    }

    @Override
    public void moveThemePreview(boolean toRight) {
        int currentActiveNav = 0;

        RelativeLayout.LayoutParams activeParams = new RelativeLayout.LayoutParams(menuXSize, ViewGroup.LayoutParams.MATCH_PARENT);
        activeParams.leftMargin = 0;
        activeParams.rightMargin = 0;
        RelativeLayout.LayoutParams prevParams = new RelativeLayout.LayoutParams(menuXSize, ViewGroup.LayoutParams.MATCH_PARENT);
        prevParams.leftMargin = menuXSize;
        prevParams.rightMargin = 0;
        RelativeLayout.LayoutParams nextParams = new RelativeLayout.LayoutParams(menuXSize, ViewGroup.LayoutParams.MATCH_PARENT);
        nextParams.leftMargin = 0;
        nextParams.rightMargin = menuXSize;

        for (int i = 0; i < 3; i++){
            if(themePreviewNavMatrix[i] == 1) currentActiveNav = i;
            themePreviewNavMatrix[i] = 0;
            themePreviewNavItemsList.get(i).getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_IC_COLOR), PorterDuff.Mode.SRC_IN);
        }

        if(toRight){
            if(currentActiveNav != 2) {
                currentActiveNav++;
            }
        } else {
            if(currentActiveNav != 0) currentActiveNav--;
        }

        themePreviewNavMatrix[currentActiveNav] = 1;

        TransitionManager.beginDelayedTransition(themePreviewContainer, new ChangeBounds()
                .setDuration(300));

        for(View v : themePreviewScreenMatrix){
            int num = themePreviewScreenMatrix.indexOf(v);
            if (num == currentActiveNav) {
                v.setLayoutParams(activeParams);
            } else if (num > currentActiveNav) {
                v.setLayoutParams(nextParams);
            } else if (num < currentActiveNav) {
                v.setLayoutParams(prevParams);
            }
        }

        themePreviewNavItemsList.get(currentActiveNav).getBackground().setColorFilter(mapColor.get(ThemeConstants.AC_1), PorterDuff.Mode.SRC_IN);
    }

    private void setTheme(){
        SharedPreferences.Editor editor = themePref.edit();

        for(Map.Entry<String, String> entry : tempTheme.getColorRow().entrySet())
            editor.putString(entry.getKey(), entry.getValue());

        editor.putString("test", "#ff0000");

        editor.apply();

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    private void switchRatePopup(boolean toOpen) {
        if(toOpen) {
            ratePopupBlur.setVisibility(View.VISIBLE);
            ratePopupContainer.setVisibility(View.VISIBLE);

            Animation opacityIn = AnimationUtils.loadAnimation(context, R.anim.opacity_in_anim_long);
            Animation dropIn = AnimationUtils.loadAnimation(context, R.anim.popup_show_anim_long);
            ratePopupBlur.startAnimation(opacityIn);
            ratePopupContainer.startAnimation(dropIn);

            View.OnClickListener btnYesClicklistener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    generalAttention.rateYesEvent();
                }
            };
            ratePopupBtnYes.setOnClickListener(btnYesClicklistener);

            View.OnClickListener btnNoClicklistener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    generalAttention.rateNoEvent();
                }
            };
            ratePopupBtnNo.setOnClickListener(btnNoClicklistener);

            View.OnClickListener btnNeverClicklistener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    generalAttention.rateNeverEvent();
                }
            };
            ratePopupBtnNever.setOnClickListener(btnNeverClicklistener);
        } else {
            Animation opacityOut = AnimationUtils.loadAnimation(context, R.anim.opacity_out_anim_long);
            Animation dropOut = AnimationUtils.loadAnimation(context, R.anim.popup_hide_anim_long);
            ratePopupBlur.startAnimation(opacityOut);
            ratePopupContainer.startAnimation(dropOut);

            ratePopupBlur.setVisibility(View.GONE);
            ratePopupContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public float dpToPixel(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    //Theme
    @Override
    public void createThemeWrapsList() {
            //MainScreen
        //topline
        themeWrapsList.add(new ThemeViewWrap(mainTopline, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_3));
        //bgc
        themeWrapsList.add(new ThemeViewWrap(mainContainer, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_1));
        //addBtn
        themeWrapsList.add(new ThemeViewWrap(addNoteBtn, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.AC_1));
        //addBtnText
        themeWrapsList.add(new ThemeViewWrap(addNoteBtn, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_LIGHT));
        //menuBtn
        themeWrapsList.add(new ThemeViewWrap(menuBtn, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK));
        //Title
        themeWrapsList.add(new ThemeViewWrap(titleNote, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        //changeBtn
        themeWrapsList.add(new ThemeViewWrap(changeBtn, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_LIGHT));

            //Menu
        //bgc
        themeWrapsList.add(new ThemeViewWrap(menuMain, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_3));
        //tagBtn
        themeWrapsList.add(new ThemeViewWrap(tagBtnTextView, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        //tagIc
        themeWrapsList.add(new ThemeViewWrap(tagAngelDown, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK));
        //changeBtn(Text)
        themeWrapsList.add(new ThemeViewWrap(changeMenuBtn, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        //tagEt
        themeWrapsList.add(new ThemeViewWrap(tagNameEt, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_1));
        //tagEtText
        themeWrapsList.add(new ThemeViewWrap(tagNameEt, ThemeWrapType.EDITTEXT_TEXT, ThemeConstants.TEXT_LIGHT));
        //tagEtHint(hint)
        themeWrapsList.add(new ThemeViewWrap(tagNameEt, ThemeWrapType.EDITTEXT_HINT, ThemeConstants.HINT));
        //categoryEt
        themeWrapsList.add(new ThemeViewWrap(categoryNameEt, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_1));
        //categoryEtText
        themeWrapsList.add(new ThemeViewWrap(categoryNameEt, ThemeWrapType.EDITTEXT_TEXT, ThemeConstants.TEXT_LIGHT));
        //categoryEtHint(hint)
        themeWrapsList.add(new ThemeViewWrap(categoryNameEt, ThemeWrapType.EDITTEXT_HINT, ThemeConstants.HINT));
        //addCategoryBtn
        themeWrapsList.add(new ThemeViewWrap(addCategoryBtn, ThemeWrapType.RIPPLE_BOX, ThemeConstants.AC_1));
        //addIc
        themeWrapsList.add(new ThemeViewWrap(addCategoryIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_IC_COLOR));
        //allNotesView
        themeWrapsList.add(new ThemeViewWrap(allNotesTextView, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        //allCountView
        themeWrapsList.add(new ThemeViewWrap(allNotesAmount, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        //allTriangleIc
        themeWrapsList.add(new ThemeViewWrap(allNotesIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK));
        //allTopBorder
        themeWrapsList.add(new ThemeViewWrap(allNotesBorderTop, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        //allBottomBorder
        themeWrapsList.add(new ThemeViewWrap(allNotesBorderBottom, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        //settingsBox
        themeWrapsList.add(new ThemeViewWrap(menuBarSettings, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK));
        //settingsIcBtn
        themeWrapsList.add(new ThemeViewWrap(menuSettingsIcBtn, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_IC_COLOR));
        //themeBtnHideZone
        themeWrapsList.add(new ThemeViewWrap(themeBtnHideZone, ThemeWrapType.ICON_VIEW, ThemeConstants.DEEP_DARK));
        themeWrapsList.add(new ThemeViewWrap(menuIcOfferGiftBox, ThemeWrapType.ICON_VIEW, ThemeConstants.AC_1));

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
        themeWrapsList.add(new ThemeViewWrap(closeChangeModZone, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        //bgc
        themeWrapsList.add(new ThemeViewWrap(changeModContainer, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_1));
        //TagEt
        themeWrapsList.add(new ThemeViewWrap(changeTagEt, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_3));
        //TagEtText
        themeWrapsList.add(new ThemeViewWrap(changeTagEt, ThemeWrapType.EDITTEXT_TEXT, ThemeConstants.TEXT_LIGHT));
        //TagEtHint(hint)
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
        //CategoryEtText
        themeWrapsList.add(new ThemeViewWrap(changeCategoryEt, ThemeWrapType.EDITTEXT_HINT, ThemeConstants.HINT));
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

            //MenuChangeMod
        //menuChangeBarContainer
        themeWrapsList.add(new ThemeViewWrap(menuBarChangeContainer, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_1));
        //menuBarSelectIc x 3 //menuBarIc x 3
        for(View v : changeModMenuBtnsList){
            themeWrapsList.add(new ThemeViewWrap(v.findViewWithTag("ic"), ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_IC_COLOR));
            themeWrapsList.add(new ThemeViewWrap(v.findViewWithTag("ic_select"), ThemeWrapType.CLASSIC_VIEW, ThemeConstants.AC_1));
        }
        //menuBarBorder x 2
        themeWrapsList.add(new ThemeViewWrap(menuChangeBarBorder1, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_4));
        themeWrapsList.add(new ThemeViewWrap(menuChangeBarBorder2, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_4));
        //barIcons x 3
        themeWrapsList.add(new ThemeViewWrap(menuChangeBarIc1, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_IC_COLOR));
        themeWrapsList.add(new ThemeViewWrap(menuChangeBarIc2, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_IC_COLOR));
        themeWrapsList.add(new ThemeViewWrap(menuChangeBarIc3, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_IC_COLOR));
        //menuHideBlurZone
        themeWrapsList.add(new ThemeViewWrap(menuHideBlurZone, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        //menuSpotList (shape)
        themeWrapsList.add(new ThemeViewWrap(menuSpotList, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_1));
        //menuChangeMixBox (shape)
        themeWrapsList.add(new ThemeViewWrap(menuChangeMixBox, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_1));
        //menuChangeNewCategoryNameEt (shape)
        themeWrapsList.add(new ThemeViewWrap(menuChangeNewCategoryNameEt, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_3));
        //menuChangeNewCategoryNameEt (edittext_text)
        themeWrapsList.add(new ThemeViewWrap(menuChangeNewCategoryNameEt, ThemeWrapType.EDITTEXT_TEXT, ThemeConstants.TEXT_LIGHT));
        //menuChangeNewCategoryNameEt (hint)
        themeWrapsList.add(new ThemeViewWrap(menuChangeNewCategoryNameEt, ThemeWrapType.EDITTEXT_HINT, ThemeConstants.HINT));
        //menuChangeNewCategoryAddBtn (ripple)
        themeWrapsList.add(new ThemeViewWrap(menuChangeNewCategoryAddBtn, ThemeWrapType.RIPPLE_BOX, ThemeConstants.AC_1));
        //menuChangeNewCategoryAddBtn (text)
        themeWrapsList.add(new ThemeViewWrap(menuChangeNewCategoryAddBtn, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        //menuChangeMixTitle (text)
        themeWrapsList.add(new ThemeViewWrap(menuChangeMixTitle, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        //menuChangeRemoveBox (shape)
        themeWrapsList.add(new ThemeViewWrap(menuChangeRemoveBox, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_1));
        //menuChangeRemoveIc (icon)
        themeWrapsList.add(new ThemeViewWrap(menuChangeRemoveIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        //menuChangeRemoveTitle (text)
        themeWrapsList.add(new ThemeViewWrap(menuChangeRemoveTitle, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        //menuChangeRemoveBorder
        themeWrapsList.add(new ThemeViewWrap(menuChangeRemoveBorder, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        //menuChangeRemoveCategoryBtn (ripple)
        themeWrapsList.add(new ThemeViewWrap(menuChangeRemoveCategoryBtn, ThemeWrapType.RIPPLE_BOX, ThemeConstants.AC_1));
        //menuChangeRemoveCategoryBtn (btn_text)
        themeWrapsList.add(new ThemeViewWrap(menuChangeRemoveCategoryBtn, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_LIGHT));
        //menuChangeRemoveCategoryNoteBtn (ripple)
        themeWrapsList.add(new ThemeViewWrap(menuChangeRemoveCategoryNoteBtn, ThemeWrapType.RIPPLE_BOX, ThemeConstants.AC_2));
        //menuChangeRemoveCategoryNoteBtn (btn_text)
        themeWrapsList.add(new ThemeViewWrap(menuChangeRemoveCategoryNoteBtn, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_LIGHT));
        //menuChangeRemoveCancleBtn (ripple)
        themeWrapsList.add(new ThemeViewWrap(menuChangeRemoveCancleBtn, ThemeWrapType.RIPPLE_BOX, ThemeConstants.BACK_DARK));
        //menuChangeRemoveCancleBtn (btn_text)
        themeWrapsList.add(new ThemeViewWrap(menuChangeRemoveCancleBtn, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_DARK));

            //hide_zones
        //menuTagHideZone1
        themeWrapsList.add(new ThemeViewWrap(menuTagHideZone1, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_3));
        //menuTagHideZone2
        themeWrapsList.add(new ThemeViewWrap(menuTagHideZone2, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_3));
        //changeModTagHideZone1
        themeWrapsList.add(new ThemeViewWrap(changeModTagHideZone1, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));
        //changeModTagHideZone2
        themeWrapsList.add(new ThemeViewWrap(changeModTagHideZone2, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));
        //changeModCategoryHideZone1
        themeWrapsList.add(new ThemeViewWrap(changeModCategoryHideZone1, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));
        //changeModCategoryHideZone2
        themeWrapsList.add(new ThemeViewWrap(changeModCategoryHideZone2, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));

            //theme
        //themeBtn
        themeWrapsList.add(new ThemeViewWrap(themeBtn, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.DEEP_DARK));
        //themeBtnText
        themeWrapsList.add(new ThemeViewWrap(themeBtnText, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_DARK));
        //theme_screen
        themeWrapsList.add(new ThemeViewWrap(themeMainContainer, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_3));
        themeWrapsList.add(new ThemeViewWrap(themeTopline, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_1));
        themeWrapsList.add(new ThemeViewWrap(themeBackIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK));
        themeWrapsList.add(new ThemeViewWrap(themeToplineTitle, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(themeDefaultTitleIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK));
        themeWrapsList.add(new ThemeViewWrap(themeDefaultTitle, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(themeDefaultTitleLine, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(themeDefaultHideZone1, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_3));
        themeWrapsList.add(new ThemeViewWrap(themeDefaultHideZone2, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_3));
        themeWrapsList.add(new ThemeViewWrap(themeListTopHideZone, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_3));
        themeWrapsList.add(new ThemeViewWrap(themePurchasedTitleIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK));
        themeWrapsList.add(new ThemeViewWrap(themePurchasedTitle, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(themePurchasedTitleLine, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(themePurchasedHideZone1, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_3));
        themeWrapsList.add(new ThemeViewWrap(themePurchasedHideZone2, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_3));

        //theme_preview
        themeWrapsList.add(new ThemeViewWrap(themePreviewBlur, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(themePreviewNav1, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_IC_COLOR));
        themeWrapsList.add(new ThemeViewWrap(themePreviewNav2, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_IC_COLOR));
        themeWrapsList.add(new ThemeViewWrap(themePreviewNav3, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_IC_COLOR));
        themeWrapsList.add(new ThemeViewWrap(themePreviewUnderlineTop, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_DARK));
        themeWrapsList.add(new ThemeViewWrap(themePreviewUnderlineBottom, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_DARK));
        //buyBtn
        themeWrapsList.add(new ThemeViewWrap(themeBuyBtn, ThemeWrapType.SHAPE_BOX, ThemeConstants.AC_1));
        themeWrapsList.add(new ThemeViewWrap(themeBuyBtnIcBox, ThemeWrapType.SHAPE_BOX, ThemeConstants.AC_2));
        themeWrapsList.add(new ThemeViewWrap(themeBuyBtnText, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        //buyCard
        themeWrapsList.add(new ThemeViewWrap(themeBuyCardBlur, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(themeBuyCardContainer, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_1));
        themeWrapsList.add(new ThemeViewWrap(themeBuyCardContainerTopline, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_3));
        themeWrapsList.add(new ThemeViewWrap(themeBuyCardPriceBox, ThemeWrapType.SHAPE_BOX, ThemeConstants.AC_1));
        themeWrapsList.add(new ThemeViewWrap(themeBuyCardThemeTitle, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(themeBuyCardPrice, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(themeBuyCardBtn, ThemeWrapType.SHAPE_BOX, ThemeConstants.AC_1));
        themeWrapsList.add(new ThemeViewWrap(themeBuyCardBtn, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(themeBuyCardSalePrice, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(themeBuyCardSaleRedLine, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.ERROR_COLOR));
            //purchaseNotification
        themeWrapsList.add(new ThemeViewWrap(purchaseNotificationBlur, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(purchaseNotificationContainer, ThemeWrapType.SHAPE_BOX, ThemeConstants.BACK_1));
        themeWrapsList.add(new ThemeViewWrap(purchaseNotificationText, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));

            //settings
        themeWrapsList.add(new ThemeViewWrap(settingsTopline, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_3));
        themeWrapsList.add(new ThemeViewWrap(settingsBackBtnIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_IC_COLOR));
        themeWrapsList.add(new ThemeViewWrap(settingsToplineTitle, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(settingsScreen, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_1));
        themeWrapsList.add(new ThemeViewWrap(settingsMaybeLaterText, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));

            //offerPopup
        themeWrapsList.add(new ThemeViewWrap(offerPopupRibbon, ThemeWrapType.ICON_VIEW, ThemeConstants.AC_1));
        themeWrapsList.add(new ThemeViewWrap(offerPopupBox, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));
        themeWrapsList.add(new ThemeViewWrap(offerPopupIcBox2, ThemeWrapType.ICON_VIEW, ThemeConstants.DEEP_DARK));
        themeWrapsList.add(new ThemeViewWrap(offerPopupIcBox1, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK));
        themeWrapsList.add(new ThemeViewWrap(offerPopupUnderline, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(offerPopupCloseIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_IC_COLOR));
        themeWrapsList.add(new ThemeViewWrap(offerPopupText1, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(offerPopupText2, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(offerPopupBtn, ThemeWrapType.ICON_VIEW, ThemeConstants.AC_1));
        themeWrapsList.add(new ThemeViewWrap(offerPopupBtn, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_LIGHT));

            //giftPopup
        themeWrapsList.add(new ThemeViewWrap(giftPopupBlur, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(giftPopupTextBox, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));
        themeWrapsList.add(new ThemeViewWrap(giftPopupBox, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));
        themeWrapsList.add(new ThemeViewWrap(giftPopupCloseBox, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK));
        themeWrapsList.add(new ThemeViewWrap(giftPopupCloseIc, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));

            //ratePopup
        themeWrapsList.add(new ThemeViewWrap(ratePopupBlur, ThemeWrapType.CLASSIC_VIEW, ThemeConstants.BACK_DARK));
        themeWrapsList.add(new ThemeViewWrap(ratePopupContainer, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_1));
        themeWrapsList.add(new ThemeViewWrap(ratePopupText, ThemeWrapType.TEXT_VIEW, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(ratePopupBtnYes, ThemeWrapType.ICON_VIEW, ThemeConstants.AC_1));
        themeWrapsList.add(new ThemeViewWrap(ratePopupBtnYes, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(ratePopupBtnNo, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(ratePopupBtnNo, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(ratePopupBtnNever, ThemeWrapType.ICON_VIEW, ThemeConstants.BACK_DARK_LIGHT));
        themeWrapsList.add(new ThemeViewWrap(ratePopupBtnNever, ThemeWrapType.BUTTON_TEXT, ThemeConstants.TEXT_LIGHT));
    }

    @Override
    public void buildTheme(ThemeSetter themeSetter) {
        themeSetter.startThemeSet();
    }

    @Override
    public void createMapColor() {
        if(mapColor.size() > 0) mapColor.clear();

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

    private void refreshTheme() {
        themeBuyBtnIc.clearColorFilter();
    }

    private void loadThemes(){
//        Log.d(SECOND_LOG, "loadThemes");
        if(themes.size() > 0) themes.clear();
        themes = new Themes(context).getThemes();

        if(generalAttention.checkGiftTheme()) {
            ThemeGift themeGift = new ThemeGift();
            themes.put("theme_gift", new Theme("Gift theme", "theme_gift", "collection_default", Themes.DEFAULTS, "0", themeGift.getColorMap(), themeGift.getIntegerColorMap(), true));
        }
    }

    private void loadCollections(){
//        Log.d(SECOND_LOG, "loadCollections");
        if(collections.size() > 0) collections.clear();
        collections = new Collections().getCollections();
    }

    private void checkProducts() {
        List<String> productsId = new ArrayList<>();

        for(Map.Entry<String, Theme> entry : themes.entrySet())
            productsId.add(entry.getKey());

        for(Map.Entry<String, Collection> entry : collections.entrySet())
            productsId.add(entry.getKey());

        if(generalAttention.checkOfferSale()) {
            List<String> tempProductsId = new ArrayList<>();

            for(String s : productsId)
                tempProductsId.add(s);

            for(String s : tempProductsId)
                productsId.add(s + "_sale_50");
        }

        mInventory.load(
                Inventory.Request.create().loadAllPurchases().loadSkus(ProductTypes.IN_APP, productsId),
                new InventoryCallback()
        );
    }

    private void giftEvent() {
        ThemeGift themeGift = new ThemeGift();

        List<Theme> tempThemeList = new ArrayList<>();
        tempThemeList.add(new Theme("current", "", "", "","0", themeGift.getColorMap(), themeGift.getIntegerColorMap(), true));
        loadThemeList(giftPopupMiniatureContainer, tempThemeList, false, false);

        Animation opacityIn = AnimationUtils.loadAnimation(context, R.anim.opacity_in_anim);
        Animation dropIn = AnimationUtils.loadAnimation(context, R.anim.popup_show_anim_long);
        Animation dropInWithDelay = AnimationUtils.loadAnimation(context, R.anim.popup_show_anim_long);
        Animation dropInWithLongDelay = AnimationUtils.loadAnimation(context, R.anim.opacity_in_anim);

        dropInWithDelay.setStartOffset(300);
        dropInWithLongDelay.setStartOffset(2000);

        giftPopupBlur.setVisibility(View.VISIBLE);
        giftPopupContainer.setVisibility(View.VISIBLE);

        giftPopupBlur.startAnimation(opacityIn);
        giftPopupTextBox.startAnimation(dropIn);
        giftPopupBox.startAnimation(dropInWithDelay);
        giftPopupCloseBox.startAnimation(dropInWithLongDelay);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation opacityOut = AnimationUtils.loadAnimation(context, R.anim.opacity_out_anim);
                Animation dropOut = AnimationUtils.loadAnimation(context, R.anim.opacity_out_anim);

                giftPopupBlur.startAnimation(opacityOut);
                giftPopupContainer.startAnimation(dropOut);

                giftPopupBlur.setVisibility(View.GONE);
                giftPopupContainer.setVisibility(View.GONE);
            }
        };

        giftPopupCloseBox.setOnClickListener(clickListener);

        SharedPreferences.Editor offerEditor = getSharedPreferences(generalAttention.OFFERS, Context.MODE_PRIVATE).edit();
        offerEditor.putInt(generalAttention.OFFER_10_NOTES, generalAttention.OFFER_CLOSE);
        offerEditor.apply();
    }

    //purchase
    private class PurchaseListener extends EmptyRequestListener<Purchase> {
        @Override
        public void onSuccess(@Nonnull Purchase purchase) {
            generalAttention.productCheckout();
            showPurchaseNotification(true);
        }

        @Override
        public void onError(int response, @Nonnull Exception e) {
            showPurchaseNotification(false);
        }
    }

    private class InventoryCallback implements Inventory.Callback {
        //call when load all product
        @Override
        public void onLoaded(@Nonnull Inventory.Products products) {
            final Inventory.Product product = products.get(ProductTypes.IN_APP);

            List<Purchase> purchasesList = product.getPurchases();
            List<Sku> skus = product.getSkus();

            for(Sku s : skus) {
                String id = s.id.toString();
                String title = s.title;
                //  "inapp/*" concat
                id = id.substring(6, id.length());
                //  "* (Tag Note)" concat
                title = title.substring(0, title.length() - 11);

                if(generalAttention.checkOfferSale()) {
                    if(id.contains("_sale_50")) {
                        String tempId = id.substring(0, id.length() - 8);

                        if(tempId.substring(0, 5).equalsIgnoreCase("theme")) {
                            if (themes.containsKey(tempId)) {
                                themes.get(tempId).setPrice(s.price);
                                themes.get(tempId).setName(title);
                            }
                        } else if (tempId.substring(0, 10).equalsIgnoreCase("collection")) {
                            if (collections.containsKey(tempId)) {
                                collections.get(tempId).setName(title);
                                collections.get(tempId).setPrice(s.price);
                            }
                        }

                        if(tempProductsPriceMap.containsKey(tempId)) tempProductsPriceMap.get(tempId)[1] = s.price;
                        else tempProductsPriceMap.put(tempId, new String[]{"", s.price});
                    } else {
                        if(tempProductsPriceMap.containsKey(id)) tempProductsPriceMap.get(id)[0] = s.price;
                        else tempProductsPriceMap.put(id, new String[]{s.price, ""});
                    }
                } else {
                    if(!id.contains("_sale_50")) {
                        if(id.substring(0, 5).equalsIgnoreCase("theme")) {
                            if (themes.containsKey(id)) {
                                themes.get(id).setPrice(s.price);
                                themes.get(id).setName(title);
                            }
                        } else if (id.substring(0, 10).equalsIgnoreCase("collection")) {
                            if (collections.containsKey(id)) {
                                collections.get(id).setName(title);
                                collections.get(id).setPrice(s.price);
                            }
                        }
                    }
                }
            }

            /*for(Map.Entry<String, String[]> entry : tempProductsPriceMap.entrySet())
                Log.d(SECOND_LOG, String.format("id: %s, price: %s, salePrice: %s", entry.getKey(), entry.getValue()[0], entry.getValue()[1]));*/

            for(Purchase p : purchasesList) {
                String purchaseSku = p.sku;

                if(purchaseSku.contains("_sale_50")) purchaseSku = purchaseSku.substring(0, purchaseSku.length() - 8);

                if(purchaseSku.substring(0, 5).equalsIgnoreCase("theme")) {
                    if(themes.containsKey(purchaseSku)) themes.get(purchaseSku).setPurchased(true);
                } else if (purchaseSku.substring(0, 10).equalsIgnoreCase("collection")) {
                    if(collections.containsKey(purchaseSku)) collections.get(purchaseSku).setPurchased(true);
                    collectionsPurchasesMap.put(purchaseSku, true);
                }
            }

            for(Map.Entry<String, Collection> entry : collections.entrySet()) {
                if(entry.getValue().isPurchased()) {
                    String collectionId = entry.getValue().getId();

                    for(Map.Entry<String, Theme> entry1 : themes.entrySet())
                        if(entry1.getValue().getCollectionId().equalsIgnoreCase(collectionId)) entry1.getValue().setPurchased(true);
                }
            }

            for(Map.Entry<String, LinearLayout> entry : tempCollectionsLines.entrySet()) {
                try {
                    if(collections.get(entry.getKey()).isPurchased()) entry.getValue().setVisibility(View.GONE);
                } catch (NullPointerException e) {
                    Log.d(SECOND_LOG, "NullPointerException");
                }
            }

            List<Theme> purchasedTheme = new ArrayList<>();
            for(Map.Entry<String, Theme> entry : themes.entrySet()) {
                if(entry.getValue().isPurchased()) purchasedTheme.add(entry.getValue());
            }

            if(purchasedTheme.size() > 0) {
                themePurchasedContainer.setVisibility(View.VISIBLE);
                loadThemeList((LinearLayout) themePurchasedContainer.findViewWithTag("list_container"), purchasedTheme, true, false);
            }

            for(Map.Entry<String, Collection> entry : collections.entrySet()) {
                String collection_id = entry.getKey();

                if(!entry.getValue().isPurchased() && !collection_id.equalsIgnoreCase(Collections.DEFAULT_ID) && !collection_id.equalsIgnoreCase(Collections.PURCHASED_ID)) {
                    boolean isPurchased = true;

                    for(Map.Entry<String, Theme> entry1 : themes.entrySet()) {
                        Theme t = entry1.getValue();

                        if(t.getCollectionId().equalsIgnoreCase(collection_id)) {
                            if(!t.isPurchased()) {
                                isPurchased = false;
                                break;
                            }
                        }
                    }

                    if(isPurchased) tempCollectionsLines.get(collection_id).setVisibility(View.GONE);
                }
            }
        }
    }

    private void checkoutWhenReady() {
        //if theme || if collection
        onActivityResult(228, 322, null);

        if(isBuyCardTheme) {
            final String tempThemeProduct = !generalAttention.checkOfferSale() ? tempTheme.getId() : tempTheme.getId() + "_sale_50";

            mCheckout.whenReady(new Checkout.EmptyListener() {
                @Override
                public void onReady(@NonNull BillingRequests requests) {
                    requests.purchase(ProductTypes.IN_APP, tempThemeProduct, null, mCheckout.getPurchaseFlow());
                }
            });
        } else {
            final String tempCollectionProduct = !generalAttention.checkOfferSale() ? tempCollection.getId() : tempCollection.getId() + "_sale_50";

            mCheckout.whenReady(new Checkout.EmptyListener() {
                @Override
                public void onReady(@NonNull BillingRequests requests) {
                    requests.purchase(ProductTypes.IN_APP, tempCollectionProduct, null, mCheckout.getPurchaseFlow());
                }
            });
        }
    }

    private void showPurchaseNotification(boolean isSuccess) {
        Animation opacityInAnim = AnimationUtils.loadAnimation(context, R.anim.opacity_in_anim_long);
        Animation dropInAnimation = AnimationUtils.loadAnimation(context, R.anim.popup_show_anim_long);

        if(isSuccess) {
            purchaseNotificationAcBox.getBackground().setColorFilter(mapColor.get(ThemeConstants.AC_1), PorterDuff.Mode.SRC_IN);
            purchaseNotificationIc.setBackground(getResources().getDrawable(R.drawable.ic_purchase_success));
            purchaseNotificationIc.clearColorFilter();

            purchaseNotificationText.setText(getResources().getText(R.string.purchase_success));
        } else {
            purchaseNotificationAcBox.getBackground().setColorFilter(mapColor.get(ThemeConstants.ERROR_COLOR), PorterDuff.Mode.SRC_IN);
            purchaseNotificationIc.setBackground(getResources().getDrawable(R.drawable.ic_error_triangle));
            purchaseNotificationIc.getBackground().setColorFilter(mapColor.get(ThemeConstants.BACK_DARK), PorterDuff.Mode.SRC_IN);

            purchaseNotificationText.setText(getResources().getText(R.string.purchase_error));
        }

        purchaseNotificationBlur.setVisibility(View.VISIBLE);
        purchaseNotificationContainer.setVisibility(View.VISIBLE);
        purchaseNotificationBlur.setAnimation(opacityInAnim);
        purchaseNotificationContainer.setAnimation(dropInAnimation);

        themes.clear();
        isThemeScreenOpen = false;
        switchThemeScreen(false);
    }

    //settings
    private void switchSettingsScreen(boolean toOpen) {
        TransitionManager.beginDelayedTransition(settingsScreen, new ChangeBounds()
                .setDuration(250)
                .setInterpolator(new AccelerateDecelerateInterpolator()));

        Animation opacityIn = AnimationUtils.loadAnimation(context, R.anim.opacity_in_anim_long);
        opacityIn.setStartOffset(300);

        if(toOpen) {
//            settingsScreen.setVisibility(View.VISIBLE);
            settingsScreenParams.leftMargin = 0;
            settingsScreen.setLayoutParams(settingsScreenParams);

            settingsMaybeLaterIc.startAnimation(opacityIn);

            isSettingsOpen = true;
        } else {
//            settingsScreen.setVisibility(View.GONE);
            settingsScreenParams.leftMargin = -menuXSize;
            settingsScreen.setLayoutParams(settingsScreenParams);

            isSettingsOpen = false;
        }
    }

    public class GeneralAttention {
        public final String CTA = "CTA";
        public final String LOREM = "lorem";
        public final String GREETINGS = "greetings";
        public final String OFFERS = "offers";
        public final String RATE = "rate";

        public final String GREETINGS_UPDATE_DATE = "greetings_update_date";

        private final String GREETINGS_NIGHT = "greetings_night";
        private final String GREETINGS_MORNING = "greetings_morning";
        private final String GREETINGS_DAY = "greetings_day";
        private final String GREETINGS_EVENING = "greetings_evening";

        private final String OFFER_FIRST = "offer_first";
        private final String OFFER_10_NOTES = "offer_10_notes";
        private final String OFFER_20_NOTES = "offer_20_notes";
        private final String OFFER_FIRST_DATE = "offer_first_date";
        private final String OFFER_FIRST_COUNT = "offer_first_count";

        private final int OFFER_WAIT = 0;
        private final int OFFER_READY = 1;
        private final int OFFER_ACTIVE = 2;
        private final int OFFER_CLOSE = 3;

        private final String RATE_SHOW_COUNT = "rate_show_count";
        private final String RATE_NO_COUNT = "rate_no_count";
        private final String RATE_NEVER = "rate_never";
        private final String RATE_YES_CLICK = "rate_yes_click";
        private final int RATE_I = 10;
        private final int RATE_NO_BARRIER = 2;

        private final SharedPreferences greetingsPref = context.getSharedPreferences(GREETINGS, Context.MODE_PRIVATE);
        private final SharedPreferences offersPref = context.getSharedPreferences(OFFERS, Context.MODE_PRIVATE);
        private final SharedPreferences ratePref = context.getSharedPreferences(RATE, Context.MODE_PRIVATE);

        //check and start needed process
        public String rankAttention() {
            String result = "";

            if(isOfferNeeded()) {
                //offer
                result = getResources().getString(R.string.attention_offer_gift);
            } else {
                if(isGreetingsNeed()) {
                    //greetings
                    result = getGreetings();
                } else {
                    if((int)(Math.random() * 11 + 1) <= 5) {
                        result = getResources().getString(R.string.theme);
                    } else {
                        if((int)(Math.random() * 100 + 1) <= 70) {
                            //lorem
                            result = getLoremAttention();
                        } else {
                            //cta
                            result = getCtaAttention();
                        }
                    }
                }
            }

            return result;
        }

        public void checkOffersAttention() {
            generateOffers();
            checkOffers();

//            logAllOfferPref();

            offerDelegat();
        }

        private String getLoremAttention() {
            String result = "";

            List<String> loremList = new ArrayList<>();
            loremList.add(context.getResources().getString(R.string.attention_lorem_1));
            loremList.add(context.getResources().getString(R.string.attention_lorem_2));
            loremList.add(context.getResources().getString(R.string.attention_lorem_3));
            loremList.add(context.getResources().getString(R.string.attention_lorem_4));
            loremList.add(context.getResources().getString(R.string.attention_lorem_5));
            loremList.add(context.getResources().getString(R.string.attention_lorem_6));
            loremList.add(context.getResources().getString(R.string.attention_lorem_7));
            loremList.add(context.getResources().getString(R.string.attention_lorem_8));
            loremList.add(context.getResources().getString(R.string.attention_lorem_9));
            loremList.add(context.getResources().getString(R.string.attention_lorem_10));
            loremList.add(context.getResources().getString(R.string.attention_lorem_11));
            loremList.add(context.getResources().getString(R.string.attention_lorem_12));
            loremList.add(context.getResources().getString(R.string.attention_lorem_13));
            loremList.add(context.getResources().getString(R.string.attention_lorem_14));
            loremList.add(context.getResources().getString(R.string.attention_lorem_15));

            result = loremList.get((int)(Math.random() * (loremList.size()-1) + 0));

            return result;
        }

        private String getCtaAttention() {
            String result = "";

            List<String> ctaList = new ArrayList<>();
            ctaList.add(context.getResources().getString(R.string.attention_cta_1));
            ctaList.add(context.getResources().getString(R.string.attention_cta_2));
            ctaList.add(context.getResources().getString(R.string.attention_cta_3));
            ctaList.add(context.getResources().getString(R.string.attention_cta_4));
            ctaList.add(context.getResources().getString(R.string.attention_cta_5));
            ctaList.add(context.getResources().getString(R.string.attention_cta_6));

            result = ctaList.get((int)(Math.random() * (ctaList.size()-1) + 0));

            return result;
        }

        private String getGreetings() {
            String result = "";
            String updatedTime = "";

            Calendar calendar = Calendar.getInstance();
            int hours = calendar.getTime().getHours();

            if(hours >= 23 || hours < 5) {
                result = context.getResources().getString(R.string.attention_greetings_night);
                updatedTime = GREETINGS_NIGHT;
            } else if(hours >= 5 && hours < 12) {
                result = context.getResources().getString(R.string.attention_greetings_morning);
                updatedTime = GREETINGS_MORNING;
            } else if(hours >= 12 && hours < 18) {
                result = context.getResources().getString(R.string.attention_greetings_day);
                updatedTime = GREETINGS_DAY;
            } else if(hours >= 18 && hours < 23) {
                result = context.getResources().getString(R.string.attention_greetings_evening);
                updatedTime = GREETINGS_EVENING;
            }

            SharedPreferences.Editor editor = greetingsPref.edit();
            editor.putBoolean(updatedTime, true);
            editor.apply();

            return result;
        }

        private boolean isGreetingsNeed() {
//            logAllGreetingsPref();
            boolean result = false;

            if(!greetingsPref.contains(GREETINGS_NIGHT)) generateGreetingsCache();

            //check last date
            refreshGreetingsDate();
//            logAllGreetingsPref();

            Calendar calendar = Calendar.getInstance();
            int hours = calendar.getTime().getHours();

            if(hours >= 23 || hours < 5) {
                boolean greetingsCheck = greetingsPref.getBoolean(GREETINGS_NIGHT, true);
                result = !greetingsCheck;
            } else if(hours >= 5 && hours < 12) {
                boolean greetingsCheck = greetingsPref.getBoolean(GREETINGS_MORNING, true);
                result = !greetingsCheck;
            } else if(hours >= 12 && hours < 18) {
                boolean greetingsCheck = greetingsPref.getBoolean(GREETINGS_DAY, true);
                result = !greetingsCheck;
            } else if(hours >= 18 && hours < 23) {
                boolean greetingsCheck = greetingsPref.getBoolean(GREETINGS_EVENING, true);
                result = !greetingsCheck;
            }

            return result;
        }

        private void generateGreetingsCache() {
            SharedPreferences.Editor greetingsEditor = greetingsPref.edit();

            greetingsEditor.putBoolean(GREETINGS_NIGHT, false);
            greetingsEditor.putBoolean(GREETINGS_MORNING, false);
            greetingsEditor.putBoolean(GREETINGS_DAY, false);
            greetingsEditor.putBoolean(GREETINGS_EVENING, false);

            greetingsEditor.putString(GREETINGS_UPDATE_DATE, "0, 0");

            greetingsEditor.apply();
        }

        private void refreshGreetingsDate() {
            Calendar calendar = Calendar.getInstance();
            boolean is = false;
            if(!greetingsPref.contains(GREETINGS_NIGHT)) generateGreetingsCache();

            String[] updateDate = greetingsPref.getString(GREETINGS_UPDATE_DATE, "1, 1").split(", ");
            int yearDay = Integer.parseInt(updateDate[0]);
            int year = Integer.parseInt(updateDate[1]);
            int currentYearDay = calendar.get(Calendar.DAY_OF_YEAR);
            int currentYear = calendar.getTime().getYear();

            if(currentYear <= year) {
                if(currentYearDay > yearDay) is = true;
            } else if(currentYear > year) {
                is = true;
            }

            if(is) {
                SharedPreferences.Editor editor = greetingsPref.edit();

                editor.putBoolean(GREETINGS_NIGHT, false);
                editor.putBoolean(GREETINGS_MORNING, false);
                editor.putBoolean(GREETINGS_DAY, false);
                editor.putBoolean(GREETINGS_EVENING, false);

                editor.putString(GREETINGS_UPDATE_DATE, String.format("%d, %d", currentYearDay, currentYear));

                editor.apply();
            }
        }

        private boolean isOfferNeeded() {
            boolean result = false;

            //check first login
            //check 10 notes
            //check 20 notes
            if(!offersPref.contains(OFFER_FIRST)) generateOffers();
            checkOffers();

//            refreshOffersCache();

            int firstTimeState = offersPref.getInt(OFFER_FIRST, OFFER_CLOSE);
            int tenTimeState = offersPref.getInt(OFFER_10_NOTES, OFFER_CLOSE);
            int twentyTimeState = offersPref.getInt(OFFER_20_NOTES, OFFER_CLOSE);

            if(firstTimeState == OFFER_ACTIVE || firstTimeState == OFFER_READY) result = true;
            if(tenTimeState == OFFER_ACTIVE || tenTimeState == OFFER_READY) result = true;
            if(twentyTimeState == OFFER_ACTIVE || twentyTimeState == OFFER_READY) result = true;

            return result;
        }

        private void generateOffers() {
            if(!offersPref.contains(OFFER_FIRST)) {
                SharedPreferences.Editor editor = offersPref.edit();

                editor.putInt(OFFER_FIRST, OFFER_WAIT);
                editor.putInt(OFFER_10_NOTES, OFFER_WAIT);
                editor.putInt(OFFER_20_NOTES, OFFER_WAIT);

                editor.apply();
            }
        }

        //temporary
        private void refreshOffersCache() {
            SharedPreferences.Editor editor = offersPref.edit();

            editor.putInt(OFFER_FIRST, OFFER_WAIT);
            editor.putInt(OFFER_10_NOTES, OFFER_WAIT);
            editor.putInt(OFFER_20_NOTES, OFFER_WAIT);

            editor.apply();
        }

        private void checkOffers() {
            SharedPreferences.Editor editor = offersPref.edit();
            int allNotesAmount = mainPresenter.getAllNotes().size();

            if(offersPref.getInt(OFFER_FIRST, OFFER_CLOSE) == OFFER_WAIT) {
                if(!offersPref.contains(OFFER_FIRST_COUNT)) {
                    editor.putInt(OFFER_FIRST_COUNT, 0);
                } else {
                    if(offersPref.getInt(OFFER_FIRST_COUNT, 0) == 1) editor.putInt(OFFER_FIRST, OFFER_READY);
                    else editor.putInt(OFFER_FIRST_COUNT, offersPref.getInt(OFFER_FIRST_COUNT, 0) + 1);
                }
            }

            if(allNotesAmount >= 10) {
                if(offersPref.getInt(OFFER_10_NOTES, OFFER_CLOSE) == OFFER_WAIT) editor.putInt(OFFER_10_NOTES, OFFER_READY);
            }

            if(allNotesAmount >= 20) {
                if(offersPref.getInt(OFFER_20_NOTES, OFFER_CLOSE) == OFFER_WAIT) editor.putInt(OFFER_20_NOTES, OFFER_READY);
            }

            editor.apply();
        }

        private void offerDelegat() {
            SharedPreferences.Editor editor = offersPref.edit();
            int firstTimeState = offersPref.getInt(OFFER_FIRST, OFFER_CLOSE);
            int tenTimeState = offersPref.getInt(OFFER_10_NOTES, OFFER_CLOSE);
            int twentyTimeState = offersPref.getInt(OFFER_20_NOTES, OFFER_CLOSE);

            if(firstTimeState == OFFER_READY) {
                //put start date
                //switch to ACTIVE
                //show popup
                //set str
                Date currentDate = new Date();
                Date tomorrowDate = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24));
                SimpleDateFormat formatter = new SimpleDateFormat("hh.mm.dd.MM.yyyy");

                editor.putString(OFFER_FIRST_DATE, formatter.format(tomorrowDate));
                editor.putInt(OFFER_FIRST, OFFER_ACTIVE);

                //show popup
                setOfferPopup(OFFER_FIRST);
                switchOfferPopup(true);
            } else if(firstTimeState == OFFER_ACTIVE) {
                //check condition
                    //set str -or- set lorem str + deactivate state
                SimpleDateFormat formatter = new SimpleDateFormat("hh.mm.dd.MM.yyyy");
                try {
                    Date currentDate = new Date();
                    Date endDate = formatter.parse(offersPref.getString(OFFER_FIRST_DATE, "01.01.01.01.2000"));

                    if(!currentDate.before(endDate)) {
                        editor.putInt(OFFER_FIRST, OFFER_CLOSE);
                    }
                } catch (ParseException e){
                    Log.d(SECOND_LOG, "!!!!!ERROR: ParseException -----");
                }
            }

            if(tenTimeState == OFFER_READY) {
                //show popup
                setOfferPopup(OFFER_10_NOTES);
                switchOfferPopup(true);

                //switch to ACTIVE
                editor.putInt(OFFER_10_NOTES, OFFER_ACTIVE);
            }

            if(twentyTimeState == OFFER_READY) {
                //show popup
                setOfferPopup(OFFER_20_NOTES);
                switchOfferPopup(true);

                //switch to ACTIVE
                editor.putInt(OFFER_20_NOTES, OFFER_ACTIVE);
            }

            editor.apply();
        }

        public int getFirstTimeClock() {
            int result = 0;

            if(offersPref.getInt(OFFER_FIRST, OFFER_CLOSE) == OFFER_ACTIVE) {
                SimpleDateFormat formatter = new SimpleDateFormat("hh.mm.dd.MM.yyyy");
                String endOfferDateStr = offersPref.getString(OFFER_FIRST_DATE, "01.01.01.01.2000");

                try {
                    Date endOfferDate = formatter.parse(endOfferDateStr);
                    Date currentDate = new Date();

                    if(currentDate.before(endOfferDate)) {
                        result = (int)(endOfferDate.getTime() - currentDate.getTime());
                    }
                } catch (ParseException e) {
                    Log.d(LOG, "ParseException in getFirstTimeClock()");
                }
            }

            return result;
        }

        public boolean checkOfferSale() {
            int offerFirstState = offersPref.getInt(OFFER_FIRST, OFFER_CLOSE);
            int offerTwentyState = offersPref.getInt(OFFER_20_NOTES, OFFER_CLOSE);

            return offerFirstState == OFFER_ACTIVE || offerTwentyState == OFFER_ACTIVE;
        }

        public String getOfferType() {
            String result = "";

            if(offersPref.getInt(OFFER_20_NOTES, OFFER_CLOSE) == OFFER_ACTIVE) result = OFFER_20_NOTES;
            if(offersPref.getInt(OFFER_FIRST, OFFER_CLOSE) == OFFER_ACTIVE) result = OFFER_FIRST;

            return result;
        }

        public boolean checkGift() {
            int offerTenState = offersPref.getInt(OFFER_10_NOTES, OFFER_CLOSE);
            return offerTenState == OFFER_ACTIVE;
        }

        public boolean checkGiftTheme() {
            int offerTenState = offersPref.getInt(OFFER_10_NOTES, OFFER_CLOSE);
            return offerTenState == OFFER_ACTIVE || offerTenState == OFFER_CLOSE;
        }

        public void productCheckout() {
            if(offersPref.getInt(OFFER_20_NOTES, OFFER_CLOSE) == OFFER_ACTIVE) {
                SharedPreferences.Editor editor = offersPref.edit();
                editor.putInt(OFFER_20_NOTES, OFFER_CLOSE);
                editor.apply();
            }
        }

        public boolean checkRatePopup() {
            boolean result = false;

            SharedPreferences.Editor editor = ratePref.edit();

            if(!ratePref.contains(RATE_NEVER)) editor.putBoolean(RATE_NEVER, false);
            if(!ratePref.contains(RATE_NO_COUNT)) editor.putInt(RATE_NO_COUNT, -1);

            if(!ratePref.getBoolean(RATE_NEVER, true) && ratePref.getInt(RATE_NO_COUNT, 0) < RATE_NO_BARRIER) {
                if(!ratePref.contains(RATE_SHOW_COUNT)) editor.putInt(RATE_SHOW_COUNT, 0);
                int rateShowCount = ratePref.getInt(RATE_SHOW_COUNT, 0);

                if(rateShowCount == RATE_I) {
                    result = true;
                    editor.putInt(RATE_SHOW_COUNT, 0);
                } else {
                    editor.putInt(RATE_SHOW_COUNT, rateShowCount+1);
                }
            }

            editor.apply();

            return result;
        }

        public boolean checkRateYesClick() {
            SharedPreferences.Editor editor = ratePref.edit();

            if(!ratePref.contains(RATE_YES_CLICK)) editor.putBoolean(RATE_YES_CLICK, false);

            editor.apply();

            return ratePref.getBoolean(RATE_YES_CLICK, false);
        }

        public void rateYesEvent() {
            SharedPreferences.Editor editor = ratePref.edit();
            editor.putBoolean(RATE_YES_CLICK, true);
            editor.apply();

            switchRatePopup(false);

            final String appPackageName = context.getPackageName();

            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }

        public void rateNoEvent() {
            SharedPreferences.Editor editor = ratePref.edit();

            if(ratePref.contains(RATE_NO_COUNT)) {
                editor.putInt(RATE_NO_COUNT, ratePref.getInt(RATE_NO_COUNT, 0)+1);
            } else {
                editor.putInt(RATE_NO_COUNT, 0);
            }

            editor.apply();

            switchRatePopup(false);
        }

        public void rateNeverEvent() {
            SharedPreferences.Editor editor = ratePref.edit();
            editor.putBoolean(RATE_NEVER, true);
            editor.apply();

            switchRatePopup(false);
        }
    }

    private void switchOfferPopup(boolean toOpen) {
        if(toOpen) {
            Animation opacityIn = AnimationUtils.loadAnimation(context, R.anim.opacity_in_anim);
            Animation dropIn = AnimationUtils.loadAnimation(context, R.anim.popup_show_anim_long);

            offerPopupBlur.setVisibility(View.VISIBLE);
            offerPopupContainer.setVisibility(View.VISIBLE);

            offerPopupBlur.startAnimation(opacityIn);
            offerPopupContainer.startAnimation(dropIn);
        } else {
            Animation opacityOut = AnimationUtils.loadAnimation(context, R.anim.opacity_out_anim);
            Animation dropOut = AnimationUtils.loadAnimation(context, R.anim.popup_hide_anim_long);

            offerPopupBlur.startAnimation(opacityOut);
            offerPopupContainer.startAnimation(dropOut);

            offerPopupBlur.setVisibility(View.GONE);
            offerPopupContainer.setVisibility(View.GONE);
        }
    }

    private void setOfferPopup(String type) {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchOfferPopup(false);
                outFixMenu(0, 0, true);
                switchThemeScreen(true);
            }
        };

        offerPopupBtn.setOnClickListener(clickListener);

        if(type.equalsIgnoreCase("offer_first")) {
            offerPopupText1.setText(getResources().getString(R.string.offer_popup_first_time_1));
            offerPopupText2.setText(getResources().getString(R.string.offer_popup_first_time_2));

            offerPopupIc.setBackground(getResources().getDrawable(R.drawable.ic_balloons));

            offerPopupText1.setTypeface(Typeface.DEFAULT);
            offerPopupText2.setTypeface(Typeface.DEFAULT_BOLD);
        } else if(type.equalsIgnoreCase("offer_10_notes")) {
            offerPopupText1.setText(getResources().getString(R.string.offer_popup_10_notes_1));
            offerPopupText2.setText(getResources().getString(R.string.offer_popup_10_notes_2));

            offerPopupIc.setBackground(getResources().getDrawable(R.drawable.ic_gift_10));

            offerPopupText2.setTypeface(Typeface.DEFAULT);
            offerPopupText1.setTypeface(Typeface.DEFAULT_BOLD);
        } else if(type.equalsIgnoreCase("offer_20_notes")) {
            offerPopupText1.setText(getResources().getString(R.string.offer_popup_20_notes_1));
            offerPopupText2.setText(getResources().getString(R.string.offer_popup_20_notes_2));

            offerPopupIc.setBackground(getResources().getDrawable(R.drawable.ic_gift_20));

            offerPopupText1.setTypeface(Typeface.DEFAULT);
            offerPopupText2.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    private void startIcAttention() {
        int msUntilOfferClose = generalAttention.getFirstTimeClock();

        if(msUntilOfferClose != 0 && msUntilOfferClose != -1) {
            //1000 * 60 * 60 * 24
            //'timer'
            menuOfferTimerBox.setVisibility(View.VISIBLE);
            menuIcOfferGiftBox.setVisibility(View.GONE);
        } else {
            if(generalAttention.getOfferType().equals(generalAttention.OFFER_20_NOTES)) {
                //gift ic
                menuOfferTimerBox.setVisibility(View.GONE);
                menuIcOfferGiftBox.setVisibility(View.VISIBLE);
            }
        }
    }
}
