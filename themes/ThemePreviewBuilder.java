package com.mobile.kiril.tagnote.themes;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mobile.kiril.tagnote.R;

import java.util.Map;

public class ThemePreviewBuilder {
    private LinearLayout container1, container2, container3;
    private Map<String, Integer> themeMapColor;
    private Context context;

    public ThemePreviewBuilder(Context context, LinearLayout container1, LinearLayout container2, LinearLayout container3, Map<String, Integer> themeMapColor) {
        this.container1 = container1;
        this.container2 = container2;
        this.container3 = container3;
        this.themeMapColor = themeMapColor;
        this.context = context;
    }

    public void buildScreen1() {
        container1.removeAllViews();

        Drawable tagShape = context.getResources().getDrawable(R.drawable.tag_shape);
        Drawable darkTagShape = tagShape;
        Drawable tagShape2 = context.getResources().getDrawable(R.drawable.tag_shape_dark);
        Drawable darkLightTagShape = tagShape2;
        darkTagShape.setColorFilter(themeMapColor.get(ThemeConstants.TEXT_LIGHT), PorterDuff.Mode.SRC_IN);
        darkLightTagShape.setColorFilter(themeMapColor.get(ThemeConstants.TEXT_LIGHT), PorterDuff.Mode.SRC_IN);
        int leftMargin = (int)dpToPixel(14);
        int textHeight = (int)dpToPixel(12);

        //bgcBox
        LinearLayout bgcBox = new LinearLayout(context);
        bgcBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bgcBox.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_1));
        bgcBox.setOrientation(LinearLayout.VERTICAL);
            //topline
        LinearLayout topline = new LinearLayout(context);
        LinearLayout.LayoutParams toplienParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(45));
        topline.setLayoutParams(toplienParams);
        topline.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_3));
                //menuBtnBox
        RelativeLayout menuBtnBox = new RelativeLayout(context);
        LinearLayout.LayoutParams menuBtnBoxParams = new LinearLayout.LayoutParams((int)dpToPixel(45), (int)dpToPixel(45));
        menuBtnBox.setLayoutParams(menuBtnBoxParams);
                    //menuBtn
        ImageView menuBtn = new ImageView(context);
        RelativeLayout.LayoutParams menuBtnParams = new RelativeLayout.LayoutParams((int)dpToPixel(30), (int)dpToPixel(30));
        menuBtnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        menuBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);
        menuBtn.setLayoutParams(menuBtnParams);
        menuBtn.setBackground(context.getResources().getDrawable(R.drawable.ic_menu));
        menuBtn.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_DARK), PorterDuff.Mode.SRC_IN);
                //titleBox
        RelativeLayout titleBox = new RelativeLayout(context);
        LinearLayout.LayoutParams titleBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.7f);
        titleBox.setLayoutParams(titleBoxParams);
                    //title
        LinearLayout title = new LinearLayout(context);
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(10));
        titleParams.rightMargin = (int)dpToPixel(40);
        titleParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        titleParams.addRule(RelativeLayout.CENTER_VERTICAL);
        title.setLayoutParams(titleParams);
        title.setBackground(darkLightTagShape);
                //changeBtnBox
        RelativeLayout changeBtnBox = new RelativeLayout(context);
        LinearLayout.LayoutParams changeBtnBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.3f);
        changeBtnBox.setLayoutParams(changeBtnBoxParams);
                    //changeBtn
        LinearLayout changeBtn = new LinearLayout(context);
        RelativeLayout.LayoutParams changeBtnParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(7));
        changeBtnParams.setMargins((int)dpToPixel(15), 0, (int)dpToPixel(15), 0);
        changeBtnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        changeBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);
        changeBtn.setLayoutParams(changeBtnParams);
        changeBtn.setBackground(darkLightTagShape);
            //contentContainer
        LinearLayout contentContainer = new LinearLayout(context);
        LinearLayout.LayoutParams contentContainerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        contentContainer.setLayoutParams(contentContainerParams);
        contentContainer.setOrientation(LinearLayout.VERTICAL);
                //itemBox1
        RelativeLayout itemBox1 = new RelativeLayout(context);
        LinearLayout.LayoutParams itemBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(55));
        itemBox1.setLayoutParams(itemBoxParams);
                    //text1
        LinearLayout text1 = new LinearLayout(context);
        RelativeLayout.LayoutParams singleTextParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, textHeight);
        singleTextParams1.addRule(RelativeLayout.CENTER_VERTICAL);
        singleTextParams1.setMargins(leftMargin, 0, (int)dpToPixel(60), 0);
        text1.setLayoutParams(singleTextParams1);
        text1.setBackground(darkTagShape);
                //underline1
        LinearLayout underline1 = new LinearLayout(context);
        LinearLayout.LayoutParams underlineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(2));
        underline1.setLayoutParams(underlineParams);
        underline1.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_4));
                //itemBox2
        RelativeLayout itemBox2 = new RelativeLayout(context);
        itemBox2.setLayoutParams(itemBoxParams);
                    //text2
        LinearLayout text2 = new LinearLayout(context);
        RelativeLayout.LayoutParams textWithTagParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, textHeight);
        textWithTagParams2.setMargins(leftMargin, textHeight, (int)dpToPixel(150), 0);
        text2.setLayoutParams(textWithTagParams2);
        text2.setBackground(darkTagShape);
                    //tagBox2
        LinearLayout tagBox2 = new LinearLayout(context);
        RelativeLayout.LayoutParams tagBoxParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, textHeight);
        tagBoxParams.setMargins(leftMargin, textHeight*2 + (int)dpToPixel(5), 0, 0);
        tagBox2.setLayoutParams(tagBoxParams);
        tagBox2.setOrientation(LinearLayout.HORIZONTAL);
                        //tag21
        RelativeLayout tag21 = new RelativeLayout(context);
        LinearLayout.LayoutParams tag21Params = new LinearLayout.LayoutParams((int)dpToPixel(70), ViewGroup.LayoutParams.MATCH_PARENT);
        tag21Params.rightMargin = textHeight;
        tag21.setLayoutParams(tag21Params);
        tag21.setBackground(context.getResources().getDrawable(R.drawable.tag_shape));
        tag21.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_4), PorterDuff.Mode.SRC_IN);
                            //tag21Text
        LinearLayout tag21text = new LinearLayout(context);
        RelativeLayout.LayoutParams tagText21Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(4));
        tagText21Params.addRule(RelativeLayout.CENTER_VERTICAL);
        tagText21Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tagText21Params.setMargins(textHeight, 0, textHeight, 0);
        tag21text.setLayoutParams(tagText21Params);
        tag21text.setBackground(darkTagShape);
                        //tag22
        RelativeLayout tag22 = new RelativeLayout(context);
        LinearLayout.LayoutParams tag22Params = new LinearLayout.LayoutParams((int)dpToPixel(90), ViewGroup.LayoutParams.MATCH_PARENT);
        tag22.setLayoutParams(tag22Params);
        tag22.setBackground(context.getResources().getDrawable(R.drawable.tag_shape));
        tag22.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_4), PorterDuff.Mode.SRC_IN);
                            //tag22Text
        LinearLayout tag22text = new LinearLayout(context);
        RelativeLayout.LayoutParams tagText22Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(4));
        tagText22Params.addRule(RelativeLayout.CENTER_VERTICAL);
        tagText22Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tagText22Params.setMargins(textHeight, 0, textHeight, 0);
        tag22text.setLayoutParams(tagText22Params);
        tag22text.setBackground(darkTagShape);
                //underline2
        LinearLayout underline2 = new LinearLayout(context);
        underline2.setLayoutParams(underlineParams);
        underline2.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_4));
                //itemBox3
        RelativeLayout itemBox3 = new RelativeLayout(context);
        itemBox3.setLayoutParams(itemBoxParams);
                    //text3
        LinearLayout text3 = new LinearLayout(context);
        RelativeLayout.LayoutParams singleTextParams3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, textHeight);
        singleTextParams3.addRule(RelativeLayout.CENTER_VERTICAL);
        singleTextParams3.setMargins(leftMargin, 0, (int)dpToPixel(100), 0);
        text3.setLayoutParams(singleTextParams3);
        text3.setBackground(darkTagShape);
                //underline3
        LinearLayout underline3 = new LinearLayout(context);
        underline3.setLayoutParams(underlineParams);
        underline3.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_4));
                //itemBox4
        RelativeLayout itemBox4 = new RelativeLayout(context);
        itemBox4.setLayoutParams(itemBoxParams);
                    //text4
        LinearLayout text4 = new LinearLayout(context);
        RelativeLayout.LayoutParams singleTextParams4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, textHeight);
        singleTextParams4.addRule(RelativeLayout.CENTER_VERTICAL);
        singleTextParams4.setMargins(leftMargin, 0, (int)dpToPixel(140), 0);
        text4.setLayoutParams(singleTextParams4);
        text4.setBackground(darkTagShape);
                //underline4
        LinearLayout underline4 = new LinearLayout(context);
        underline4.setLayoutParams(underlineParams);
        underline4.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_4));
            //itemBox5
        RelativeLayout itemBox5 = new RelativeLayout(context);
        itemBox5.setLayoutParams(itemBoxParams);
                //text5
        LinearLayout text5 = new LinearLayout(context);
        RelativeLayout.LayoutParams textWithTagParams5 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, textHeight);
        textWithTagParams5.setMargins(leftMargin, textHeight, (int)dpToPixel(40), 0);
        text5.setLayoutParams(textWithTagParams5);
        text5.setBackground(darkTagShape);
                //tagBox5
        LinearLayout tagBox5 = new LinearLayout(context);
        tagBox5.setLayoutParams(tagBoxParams);
        tagBox5.setOrientation(LinearLayout.HORIZONTAL);
                    //tag51
        RelativeLayout tag51 = new RelativeLayout(context);
        LinearLayout.LayoutParams tag51Params = new LinearLayout.LayoutParams((int)dpToPixel(50), ViewGroup.LayoutParams.MATCH_PARENT);
        tag51Params.rightMargin = textHeight;
        tag51.setLayoutParams(tag51Params);
        tag51.setBackground(context.getResources().getDrawable(R.drawable.tag_shape));
        tag51.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_4), PorterDuff.Mode.SRC_IN);
                        //tag51Text
        LinearLayout tag51text = new LinearLayout(context);
        RelativeLayout.LayoutParams tagText51Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(4));
        tagText51Params.addRule(RelativeLayout.CENTER_VERTICAL);
        tagText51Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tagText51Params.setMargins(textHeight, 0, textHeight, 0);
        tag51text.setLayoutParams(tagText51Params);
        tag51text.setBackground(darkTagShape);
                    //tag52
        RelativeLayout tag52 = new RelativeLayout(context);
        LinearLayout.LayoutParams tag52Params = new LinearLayout.LayoutParams((int)dpToPixel(70), ViewGroup.LayoutParams.MATCH_PARENT);
        tag52Params.rightMargin = textHeight;
        tag52.setLayoutParams(tag52Params);
        tag52.setBackground(context.getResources().getDrawable(R.drawable.tag_shape));
        tag52.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_4), PorterDuff.Mode.SRC_IN);
                        //tag52Text
        LinearLayout tag52text = new LinearLayout(context);
        RelativeLayout.LayoutParams tagText52Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(4));
        tagText52Params.addRule(RelativeLayout.CENTER_VERTICAL);
        tagText52Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tagText52Params.setMargins(textHeight, 0, textHeight, 0);
        tag52text.setLayoutParams(tagText52Params);
        tag52text.setBackground(darkTagShape);
                    //tag53
        RelativeLayout tag53 = new RelativeLayout(context);
        LinearLayout.LayoutParams tag53Params = new LinearLayout.LayoutParams((int)dpToPixel(50), ViewGroup.LayoutParams.MATCH_PARENT);
        tag53.setLayoutParams(tag53Params);
        tag53.setBackground(context.getResources().getDrawable(R.drawable.tag_shape));
        tag53.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_4), PorterDuff.Mode.SRC_IN);
                        //tag53text
        LinearLayout tag53text = new LinearLayout(context);
        RelativeLayout.LayoutParams tagText53Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(4));
        tagText53Params.addRule(RelativeLayout.CENTER_VERTICAL);
        tagText53Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tagText53Params.setMargins(textHeight, 0, textHeight, 0);
        tag53text.setLayoutParams(tagText53Params);
        tag53text.setBackground(darkTagShape);
            //underline5
        LinearLayout underline5 = new LinearLayout(context);
        underline5.setLayoutParams(underlineParams);
        underline5.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_4));
            //addBtn
        RelativeLayout addBtn = new RelativeLayout(context);
        LinearLayout.LayoutParams addBtnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(45));
        addBtn.setLayoutParams(addBtnParams);
        addBtn.setBackgroundColor(themeMapColor.get(ThemeConstants.AC_1));
                //addBtnText
        LinearLayout addBtnText = new LinearLayout(context);
        RelativeLayout.LayoutParams addBtnTextParams = new RelativeLayout.LayoutParams((int)dpToPixel(120), (int)dpToPixel(8));
        addBtnTextParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        addBtnTextParams.addRule(RelativeLayout.CENTER_VERTICAL);
        addBtnText.setLayoutParams(addBtnTextParams);
        addBtnText.setBackground(darkLightTagShape);

        //implementation
            //topline
        menuBtnBox.addView(menuBtn);
        topline.addView(menuBtnBox);
        titleBox.addView(title);
        topline.addView(titleBox);
        changeBtnBox.addView(changeBtn);
        topline.addView(changeBtnBox);
        bgcBox.addView(topline);
            //contentContainer
        itemBox1.addView(text1);
        contentContainer.addView(itemBox1);
        contentContainer.addView(underline1);

        itemBox2.addView(text2);
            tag21.addView(tag21text);
        tagBox2.addView(tag21);
            tag22.addView(tag22text);
        tagBox2.addView(tag22);
        itemBox2.addView(tagBox2);
        contentContainer.addView(itemBox2);
        contentContainer.addView(underline2);

        itemBox3.addView(text3);
        contentContainer.addView(itemBox3);
        contentContainer.addView(underline3);

        itemBox4.addView(text4);
        contentContainer.addView(itemBox4);
        contentContainer.addView(underline4);

        itemBox5.addView(text5);
        tag51.addView(tag51text);
        tagBox5.addView(tag51);
        tag52.addView(tag52text);
        tagBox5.addView(tag52);
        tag53.addView(tag53text);
        tagBox5.addView(tag53);
        itemBox5.addView(tagBox5);
        contentContainer.addView(itemBox5);
        contentContainer.addView(underline5);

        bgcBox.addView(contentContainer);
            //addBtn
        addBtn.addView(addBtnText);
        bgcBox.addView(addBtn);
            //finish
        container1.addView(bgcBox);
    }

    public void buildScreen2() {
        container2.removeAllViews();

        int dp12 = (int)dpToPixel(12);
        int dp2 = (int)dpToPixel(2);
        Drawable tagShape = context.getResources().getDrawable(R.drawable.tag_shape_dark);
        Drawable darkTagShape = tagShape;
        darkTagShape.setColorFilter(themeMapColor.get(ThemeConstants.TEXT_LIGHT), PorterDuff.Mode.SRC_IN);
        Drawable tagShape2 = context.getResources().getDrawable(R.drawable.tag_shape_dark);
        Drawable darkLightTagShape = tagShape2;
        darkLightTagShape.setColorFilter(themeMapColor.get(ThemeConstants.TEXT_LIGHT), PorterDuff.Mode.SRC_IN);

        //bgcBox(Relative)
        RelativeLayout bgcBox = new RelativeLayout(context);
        bgcBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bgcBox.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_1));
            //backLayer
        LinearLayout backLayer = new LinearLayout(context);
        RelativeLayout.LayoutParams backLayerParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        backLayer.setLayoutParams(backLayerParams);
        backLayer.setOrientation(LinearLayout.VERTICAL);
                //backTopline
        RelativeLayout backTopline = new RelativeLayout(context);
        LinearLayout.LayoutParams backToplineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(45));
        backTopline.setLayoutParams(backToplineParams);
        backTopline.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_3));
                    //backChangeBtn
        LinearLayout backChangeBtn = new LinearLayout(context);
        RelativeLayout.LayoutParams backChangeBtnParams = new RelativeLayout.LayoutParams((int)dpToPixel(100), (int)dpToPixel(7));
        backChangeBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);
        backChangeBtnParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        backChangeBtnParams.rightMargin = (int)dpToPixel(12);
        backChangeBtn.setLayoutParams(backChangeBtnParams);
        backChangeBtn.setBackground(darkLightTagShape);
                //backSpaceBox
        LinearLayout backSpaceBox = new LinearLayout(context);
        LinearLayout.LayoutParams backSpaceBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        backSpaceBox.setLayoutParams(backSpaceBoxParams);
                //backAddBtn
        LinearLayout backAddBtn = new LinearLayout(context);
        LinearLayout.LayoutParams backAddBtnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(45));
        backAddBtn.setLayoutParams(backAddBtnParams);
        backAddBtn.setBackgroundColor(themeMapColor.get(ThemeConstants.AC_1));
            //blur
        LinearLayout blur = new LinearLayout(context);
        blur.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        blur.setAlpha(0.6f);
        blur.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_DARK));
            //menuContainer
        LinearLayout menuContainer = new LinearLayout(context);
        RelativeLayout.LayoutParams menuContainerParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        menuContainerParams.rightMargin = (int)dpToPixel(50);
        menuContainer.setLayoutParams(menuContainerParams);
        menuContainer.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_3));
        menuContainer.setOrientation(LinearLayout.VERTICAL);
                //topline(4/2/4)
        LinearLayout topline = new LinearLayout(context);
        LinearLayout.LayoutParams toplineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(35));
        topline.setLayoutParams(toplineParams);
        topline.setOrientation(LinearLayout.HORIZONTAL);
                    //tagBtnBox
        RelativeLayout tagBtnBox = new RelativeLayout(context);
        LinearLayout.LayoutParams tagBtnBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.4f);
        tagBtnBox.setLayoutParams(tagBtnBoxParams);
                        //tagBtnMarginBox
        LinearLayout tagBtnMarginBox = new LinearLayout(context);
        RelativeLayout.LayoutParams tagBtnMarginBoxParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tagBtnMarginBoxParams.addRule(RelativeLayout.CENTER_VERTICAL);
        tagBtnMarginBoxParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tagBtnMarginBox.setLayoutParams(tagBtnMarginBoxParams);
                            //tagBtnGravityBox
        RelativeLayout tagBtnGravityBox = new RelativeLayout(context);
        LinearLayout.LayoutParams tagBtnGravityBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tagBtnGravityBox.setLayoutParams(tagBtnGravityBoxParams);
                                //tagBtn
        LinearLayout tagBtn = new LinearLayout(context);
        RelativeLayout.LayoutParams tagBtnParams = new RelativeLayout.LayoutParams((int)dpToPixel(45), (int)dpToPixel(9));
        tagBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);
        tagBtnParams.rightMargin = (int)dpToPixel(7);
        tagBtnParams.leftMargin = (int)dpToPixel(16);
        tagBtn.setLayoutParams(tagBtnParams);
        tagBtn.setBackground(darkLightTagShape);
                            //tagBtnIc
        ImageView tagBtnIc = new ImageView(context);
        ViewGroup.LayoutParams tagBtnIcParmas = new ViewGroup.LayoutParams((int)dpToPixel(26), (int)dpToPixel(26));
        tagBtnIc.setLayoutParams(tagBtnIcParmas);
        tagBtnIc.setBackground(context.getResources().getDrawable(R.drawable.ic_angle_down));
        tagBtnIc.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_DARK), PorterDuff.Mode.SRC_IN);
                    //toplineSpaceBox
        LinearLayout toplineSpaceBox = new LinearLayout(context);
        LinearLayout.LayoutParams toplineSpaceBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f);
        toplineSpaceBox.setLayoutParams(toplineSpaceBoxParams);
                    //changeBtnBox
        RelativeLayout changeBtnBox = new RelativeLayout(context);
        LinearLayout.LayoutParams changeBtnBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.4f);
        changeBtnBox.setLayoutParams(changeBtnBoxParams);
                        //changeBtn
        LinearLayout changeBtn = new LinearLayout(context);
        RelativeLayout.LayoutParams changeBtnParams = new RelativeLayout.LayoutParams((int)dpToPixel(70), (int)dpToPixel(9));
        changeBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);
        changeBtnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        changeBtn.setLayoutParams(changeBtnParams);
        changeBtn.setBackground(darkLightTagShape);
                //searchContainer
        LinearLayout searchContainer = new LinearLayout(context);
        LinearLayout.LayoutParams searchContainerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(45));
        searchContainerParams.setMargins(dp12, 0, dp12, dp12);
        searchContainer.setLayoutParams(searchContainerParams);
        searchContainer.setOrientation(LinearLayout.HORIZONTAL);
                    //searchBox
        LinearLayout searchBox = new LinearLayout(context);
        LinearLayout.LayoutParams searchBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.8f);
        searchBox.setLayoutParams(searchBoxParams);
        searchBox.setBackground(context.getResources().getDrawable(R.drawable.left_rounded_shape));
        searchBox.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_1), PorterDuff.Mode.SRC_IN);
                    //addBtn
        RelativeLayout addBtn = new RelativeLayout(context);
        LinearLayout.LayoutParams addBtnParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f);
        addBtn.setLayoutParams(addBtnParams);
        addBtn.setBackground(context.getResources().getDrawable(R.drawable.right_rounded_shape));
        addBtn.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.AC_1), PorterDuff.Mode.SRC_IN);
                        //addBtnIc
        ImageView addBtnIc = new ImageView(context);
        RelativeLayout.LayoutParams addBtnIcParams = new RelativeLayout.LayoutParams((int)dpToPixel(24), (int)dpToPixel(24));
        addBtnIcParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        addBtnIcParams.addRule(RelativeLayout.CENTER_VERTICAL);
        addBtnIc.setLayoutParams(addBtnIcParams);
        addBtnIc.setBackground(context.getResources().getDrawable(R.drawable.ic_plus));
        addBtnIc.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_IC_COLOR), PorterDuff.Mode.SRC_IN);
                //listContainer
        LinearLayout listContainer = new LinearLayout(context);
        LinearLayout.LayoutParams listContainerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        listContainer.setLayoutParams(listContainerParams);
        listContainer.setOrientation(LinearLayout.VERTICAL);
                    //underlineTop
        LinearLayout underlineTop = new LinearLayout(context);
        LinearLayout.LayoutParams underlineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2);
        underlineTop.setLayoutParams(underlineParams);
        underlineTop.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_DARK_LIGHT));
                    //itemBox1
        LinearLayout itemBox1 = new LinearLayout(context);
        LinearLayout.LayoutParams itemBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(45));
        itemBox1.setLayoutParams(itemBoxParams);
        itemBox1.setOrientation(LinearLayout.HORIZONTAL);
                        //textBox1
        RelativeLayout textBox1 = new RelativeLayout(context);
        LinearLayout.LayoutParams textBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.8f);
        textBox1.setLayoutParams(textBoxParams);
                            //text1
        LinearLayout text1 = new LinearLayout(context);
        RelativeLayout.LayoutParams text1Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp12);
        text1Params.addRule(RelativeLayout.CENTER_VERTICAL);
        text1Params.rightMargin = (int)dpToPixel(30);
        text1Params.leftMargin = dp12;
        text1.setLayoutParams(text1Params);
        text1.setBackground(darkTagShape);
                        //amountBox1
        RelativeLayout amountBox1 = new RelativeLayout(context);
        LinearLayout.LayoutParams amountBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f);
        amountBox1.setLayoutParams(amountBoxParams);
                            //amountMarginBox1
        LinearLayout amountMarginBox1 = new LinearLayout(context);
        RelativeLayout.LayoutParams amountMarginBoxParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        amountMarginBoxParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        amountMarginBoxParams.addRule(RelativeLayout.CENTER_VERTICAL);
        amountMarginBox1.setLayoutParams(amountMarginBoxParams);
        amountMarginBox1.setOrientation(LinearLayout.HORIZONTAL);
                                //amount1
        LinearLayout amount1 = new LinearLayout(context);
        LinearLayout.LayoutParams amountParams = new LinearLayout.LayoutParams((int)dpToPixel(16), dp12);
        amountParams.gravity = Gravity.CENTER_VERTICAL;
        amount1.setLayoutParams(amountParams);
        amount1.setBackground(darkLightTagShape);
                                //amountIc1
        ImageView amountIc1 = new ImageView(context);
        LinearLayout.LayoutParams amountIcParams = new LinearLayout.LayoutParams((int)dpToPixel(20), (int)dpToPixel(20));
        amountIc1.setLayoutParams(amountIcParams);
        amountIc1.setBackground(context.getResources().getDrawable(R.drawable.ic_triangle_right));
        amountIc1.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_DARK_2), PorterDuff.Mode.SRC_IN);
                    //underline1
        LinearLayout underline1 = new LinearLayout(context);
        underline1.setLayoutParams(underlineParams);
        underline1.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_DARK_LIGHT));
                    //itemBox2
        LinearLayout itemBox2 = new LinearLayout(context);
        itemBox2.setLayoutParams(itemBoxParams);
        itemBox2.setOrientation(LinearLayout.HORIZONTAL);
                        //textBox2
        RelativeLayout textBox2 = new RelativeLayout(context);
        textBox2.setLayoutParams(textBoxParams);
                            //text2
        LinearLayout text2 = new LinearLayout(context);
        RelativeLayout.LayoutParams text2Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp12);
        text2Params.addRule(RelativeLayout.CENTER_VERTICAL);
        text2Params.rightMargin = (int)dpToPixel(70);
        text2Params.leftMargin = dp12;
        text2.setLayoutParams(text2Params);
        text2.setBackground(darkTagShape);
                        //amountBox2
        RelativeLayout amountBox2 = new RelativeLayout(context);
        amountBox2.setLayoutParams(amountBoxParams);
                            //amountMarginBox2
        LinearLayout amountMarginBox2 = new LinearLayout(context);
        amountMarginBox2.setLayoutParams(amountMarginBoxParams);
        amountMarginBox2.setOrientation(LinearLayout.HORIZONTAL);
                                //amount2
        LinearLayout amount2 = new LinearLayout(context);
        amount2.setLayoutParams(amountParams);
        amount2.setBackground(darkLightTagShape);
                                //amountIc2
        ImageView amountIc2 = new ImageView(context);
        amountIc2.setLayoutParams(amountIcParams);
        amountIc2.setBackground(context.getResources().getDrawable(R.drawable.ic_triangle_right));
        amountIc2.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_DARK_2), PorterDuff.Mode.SRC_IN);
                    //underline2
        LinearLayout underline2 = new LinearLayout(context);
        underline2.setLayoutParams(underlineParams);
        underline2.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_DARK_LIGHT));
                    //itemBox3
        LinearLayout itemBox3 = new LinearLayout(context);
        itemBox3.setLayoutParams(itemBoxParams);
        itemBox3.setOrientation(LinearLayout.HORIZONTAL);
                        //textBox3
        RelativeLayout textBox3 = new RelativeLayout(context);
        textBox3.setLayoutParams(textBoxParams);
                            //text3
        LinearLayout text3 = new LinearLayout(context);
        RelativeLayout.LayoutParams text3Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp12);
        text3Params.addRule(RelativeLayout.CENTER_VERTICAL);
        text3Params.rightMargin = (int)dpToPixel(50);
        text3Params.leftMargin = dp12;
        text3.setLayoutParams(text3Params);
        text3.setBackground(darkTagShape);
                        //amountBox3
        RelativeLayout amountBox3 = new RelativeLayout(context);
        amountBox3.setLayoutParams(amountBoxParams);
                            //amountMarginBox3
        LinearLayout amountMarginBox3 = new LinearLayout(context);
        amountMarginBox3.setLayoutParams(amountMarginBoxParams);
        amountMarginBox3.setOrientation(LinearLayout.HORIZONTAL);
                                //amount3
        LinearLayout amount3 = new LinearLayout(context);
        amount3.setLayoutParams(amountParams);
        amount3.setBackground(darkLightTagShape);
                                //amountIc3
        ImageView amountIc3 = new ImageView(context);
        amountIc3.setLayoutParams(amountIcParams);
        amountIc3.setBackground(context.getResources().getDrawable(R.drawable.ic_triangle_right));
        amountIc3.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_DARK_2), PorterDuff.Mode.SRC_IN);
                    //underline3
        LinearLayout underline3 = new LinearLayout(context);
        underline3.setLayoutParams(underlineParams);
        underline3.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_DARK_LIGHT));
                //settingsBox
        LinearLayout settingsBox = new LinearLayout(context);
        LinearLayout.LayoutParams settingsBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(45));
        settingsBox.setLayoutParams(settingsBoxParams);
        settingsBox.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_DARK));

        //implementation
            //backLayer
        backTopline.addView(backChangeBtn);
        backLayer.addView(backTopline);
        backLayer.addView(backSpaceBox);
        backLayer.addView(backAddBtn);
        bgcBox.addView(backLayer);
        bgcBox.addView(blur);
            //menuContaienr
                //topline
        tagBtnGravityBox.addView(tagBtn);
        tagBtnMarginBox.addView(tagBtnGravityBox);
        tagBtnMarginBox.addView(tagBtnIc);
        tagBtnBox.addView(tagBtnMarginBox);
        topline.addView(tagBtnBox);
        topline.addView(toplineSpaceBox);
        changeBtnBox.addView(changeBtn);
        topline.addView(changeBtnBox);
        menuContainer.addView(topline);
            //searchContainer
        searchContainer.addView(searchBox);
        addBtn.addView(addBtnIc);
        searchContainer.addView(addBtn);
        menuContainer.addView(searchContainer);
            //listContainer
        listContainer.addView(underlineTop);

        textBox1.addView(text1);
        itemBox1.addView(textBox1);
        amountMarginBox1.addView(amount1);
        amountMarginBox1.addView(amountIc1);
        amountBox1.addView(amountMarginBox1);
        itemBox1.addView(amountBox1);
        listContainer.addView(itemBox1);
        listContainer.addView(underline1);

        textBox2.addView(text2);
        itemBox2.addView(textBox2);
        amountMarginBox2.addView(amount2);
        amountMarginBox2.addView(amountIc2);
        amountBox2.addView(amountMarginBox2);
        itemBox2.addView(amountBox2);
        listContainer.addView(itemBox2);
        listContainer.addView(underline2);

        textBox3.addView(text3);
        itemBox3.addView(textBox3);
        amountMarginBox3.addView(amount3);
        amountMarginBox3.addView(amountIc3);
        amountBox3.addView(amountMarginBox3);
        itemBox3.addView(amountBox3);
        listContainer.addView(itemBox3);
        listContainer.addView(underline3);

        menuContainer.addView(listContainer);
            //settingsBox
        menuContainer.addView(settingsBox);
        bgcBox.addView(menuContainer);
            //final
        container2.addView(bgcBox);
    }

    public void buildScreen3() {
        container3.removeAllViews();

        Drawable tagShape2 = context.getResources().getDrawable(R.drawable.tag_shape_dark);
        Drawable darkLightTagShape = tagShape2;
        darkLightTagShape.setColorFilter(themeMapColor.get(ThemeConstants.TEXT_LIGHT), PorterDuff.Mode.SRC_IN);

        //bgcBox(Relative)
        RelativeLayout bgcBox = new RelativeLayout(context);
        bgcBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bgcBox.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_1));
        //backLayer
        LinearLayout backLayer = new LinearLayout(context);
        RelativeLayout.LayoutParams backLayerParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        backLayer.setLayoutParams(backLayerParams);
        backLayer.setOrientation(LinearLayout.VERTICAL);
            //topline
        LinearLayout topline = new LinearLayout(context);
        LinearLayout.LayoutParams toplienParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(45));
        topline.setLayoutParams(toplienParams);
        topline.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_3));
                //menuBtnBox
        RelativeLayout menuBtnBox = new RelativeLayout(context);
        LinearLayout.LayoutParams menuBtnBoxParams = new LinearLayout.LayoutParams((int)dpToPixel(45), (int)dpToPixel(45));
        menuBtnBox.setLayoutParams(menuBtnBoxParams);
                    //menuBtn
        ImageView menuBtn = new ImageView(context);
        RelativeLayout.LayoutParams menuBtnParams = new RelativeLayout.LayoutParams((int)dpToPixel(30), (int)dpToPixel(30));
        menuBtnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        menuBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);
        menuBtn.setLayoutParams(menuBtnParams);
        menuBtn.setBackground(context.getResources().getDrawable(R.drawable.ic_menu));
        menuBtn.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_DARK), PorterDuff.Mode.SRC_IN);
                //titleBox
        RelativeLayout titleBox = new RelativeLayout(context);
        LinearLayout.LayoutParams titleBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.7f);
        titleBox.setLayoutParams(titleBoxParams);
                    //title
        LinearLayout title = new LinearLayout(context);
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(10));
        titleParams.rightMargin = (int)dpToPixel(40);
        titleParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        titleParams.addRule(RelativeLayout.CENTER_VERTICAL);
        title.setLayoutParams(titleParams);
        title.setBackground(darkLightTagShape);
                //changeBtnBox
        RelativeLayout changeBtnBox = new RelativeLayout(context);
        LinearLayout.LayoutParams changeBtnBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.3f);
        changeBtnBox.setLayoutParams(changeBtnBoxParams);
                    //changeBtn
        LinearLayout changeBtn = new LinearLayout(context);
        RelativeLayout.LayoutParams changeBtnParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(7));
        changeBtnParams.setMargins((int)dpToPixel(15), 0, (int)dpToPixel(15), 0);
        changeBtnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        changeBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);
        changeBtn.setLayoutParams(changeBtnParams);
        changeBtn.setBackground(darkLightTagShape);
            //itemBox1
        RelativeLayout itemBox1 = new RelativeLayout(context);
        LinearLayout.LayoutParams itemBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(55));
        itemBox1.setLayoutParams(itemBoxParams);
                //text1
        LinearLayout text1 = new LinearLayout(context);
        RelativeLayout.LayoutParams singleTextParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(12));
        singleTextParams1.addRule(RelativeLayout.CENTER_VERTICAL);
        singleTextParams1.setMargins((int)dpToPixel(14), 0, (int)dpToPixel(60), 0);
        text1.setLayoutParams(singleTextParams1);
        text1.setBackground(context.getResources().getDrawable(R.drawable.tag_shape));
        text1.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.TEXT_LIGHT), PorterDuff.Mode.SRC_IN);
            //underline1
        LinearLayout underline1 = new LinearLayout(context);
        LinearLayout.LayoutParams underlineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(2));
        underline1.setLayoutParams(underlineParams);
        underline1.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_4));
        //mainLayer
        LinearLayout mainLayer = new LinearLayout(context);
        mainLayer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mainLayer.setOrientation(LinearLayout.VERTICAL);
            //blur
        LinearLayout blur = new LinearLayout(context);
        LinearLayout.LayoutParams blurParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0.25f);
        blur.setLayoutParams(blurParams);
        blur.setAlpha(0.6f);
        blur.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_DARK_LIGHT));
            //iconBar
        LinearLayout iconBar = new LinearLayout(context);
        LinearLayout.LayoutParams iconBarParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(55));
        iconBar.setLayoutParams(iconBarParams);
        iconBar.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_DARK));
        iconBar.setOrientation(LinearLayout.HORIZONTAL);
                //iconBox1
        RelativeLayout iconBox1 = new RelativeLayout(context);
        LinearLayout.LayoutParams iconBoxParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        iconBox1.setLayoutParams(iconBoxParams);
                    //ic1
        ImageView ic1 = new ImageView(context);
        RelativeLayout.LayoutParams icParams = new RelativeLayout.LayoutParams((int)dpToPixel(30), (int)dpToPixel(30));
        icParams.addRule(RelativeLayout.CENTER_VERTICAL);
        icParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        ic1.setLayoutParams(icParams);
        ic1.setBackground(context.getResources().getDrawable(R.drawable.ic_tag));
        ic1.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_IC_COLOR), PorterDuff.Mode.SRC_IN);
                //barUnderline1
        LinearLayout barUnderline1 = new LinearLayout(context);
        LinearLayout.LayoutParams barUnderlineParams = new LinearLayout.LayoutParams((int)dpToPixel(2), ViewGroup.LayoutParams.MATCH_PARENT);
        barUnderline1.setLayoutParams(barUnderlineParams);
        barUnderline1.setBackgroundColor(themeMapColor.get(ThemeConstants.DEEP_DARK));
                //iconBox2
        RelativeLayout iconBox2 = new RelativeLayout(context);
        iconBox2.setLayoutParams(iconBoxParams);
                    //ic2
        ImageView ic2 = new ImageView(context);
        ic2.setLayoutParams(icParams);
        ic2.setBackground(context.getResources().getDrawable(R.drawable.ic_category));
        ic2.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_IC_COLOR), PorterDuff.Mode.SRC_IN);
                //barUnderline2
        LinearLayout barUnderline2 = new LinearLayout(context);
        barUnderline2.setLayoutParams(barUnderlineParams);
        barUnderline2.setBackgroundColor(themeMapColor.get(ThemeConstants.DEEP_DARK));
                //iconBox3
        RelativeLayout iconBox3 = new RelativeLayout(context);
        iconBox3.setLayoutParams(iconBoxParams);
                    //ic3
        ImageView ic3 = new ImageView(context);
        ic3.setLayoutParams(icParams);
        ic3.setBackground(context.getResources().getDrawable(R.drawable.ic_spot));
        ic3.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_1), PorterDuff.Mode.SRC_IN);
                    //selectIc
        LinearLayout selectIc = new LinearLayout(context);
        RelativeLayout.LayoutParams selectIcParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(8));
        selectIcParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        selectIc.setLayoutParams(selectIcParams);
        selectIc.setBackgroundColor(themeMapColor.get(ThemeConstants.AC_1));
            //barUnderline3
        LinearLayout barUnderline3 = new LinearLayout(context);
        barUnderline3.setLayoutParams(barUnderlineParams);
        barUnderline3.setBackgroundColor(themeMapColor.get(ThemeConstants.DEEP_DARK));
                //iconBox4
        RelativeLayout iconBox4 = new RelativeLayout(context);
        iconBox4.setLayoutParams(iconBoxParams);
                    //ic4
        ImageView ic4 = new ImageView(context);
        ic4.setLayoutParams(icParams);
        ic4.setBackground(context.getResources().getDrawable(R.drawable.ic_remove));
        ic4.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_IC_COLOR), PorterDuff.Mode.SRC_IN);
            //spotArea
        LinearLayout spotArea = new LinearLayout(context);
        LinearLayout.LayoutParams spotAreaParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0.75f);
        spotArea.setLayoutParams(spotAreaParams);
        spotArea.setBackgroundColor(themeMapColor.get(ThemeConstants.BACK_1));
        spotArea.setOrientation(LinearLayout.VERTICAL);
                //currentSpotBox
        RelativeLayout currentSpotBox = new RelativeLayout(context);
        LinearLayout.LayoutParams currentSpotBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dpToPixel(70));
        currentSpotBox.setLayoutParams(currentSpotBoxParams);
                    //currentSpot
        ImageView currentSpot = new ImageView(context);
        RelativeLayout.LayoutParams currentSpotParams = new RelativeLayout.LayoutParams((int)dpToPixel(35), (int)dpToPixel(35));
        currentSpotParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        currentSpotParams.addRule(RelativeLayout.CENTER_VERTICAL);
        currentSpot.setLayoutParams(currentSpotParams);
        currentSpot.setBackground(context.getResources().getDrawable(R.drawable.ic_theme));
                //spotContainer
        LinearLayout spotContainer = new LinearLayout(context);
        LinearLayout.LayoutParams spotContainerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        spotContainerParams.setMargins((int)dpToPixel(18), 0, (int)dpToPixel(18), (int)dpToPixel(24));
        spotContainer.setLayoutParams(spotContainerParams);
        spotContainer.setBackground(context.getResources().getDrawable(R.drawable.popup_white_shape));
        spotContainer.getBackground().setColorFilter(themeMapColor.get(ThemeConstants.BACK_3), PorterDuff.Mode.SRC_IN);
        spotContainer.setElevation(2);
        spotContainer.setTranslationZ(4);
        //implementation
            //backLayer
                //topline
        menuBtnBox.addView(menuBtn);
        topline.addView(menuBtnBox);
        titleBox.addView(title);
        topline.addView(titleBox);
        changeBtnBox.addView(changeBtn);
        topline.addView(changeBtnBox);
        backLayer.addView(topline);
                //item
        itemBox1.addView(text1);
        backLayer.addView(itemBox1);
        backLayer.addView(underline1);
        bgcBox.addView(backLayer);
            //mainLayer
                //blur
        mainLayer.addView(blur);
                //iconBar
        iconBox1.addView(ic1);
        iconBar.addView(iconBox1);
        iconBar.addView(barUnderline1);
        iconBox2.addView(ic2);
        iconBar.addView(iconBox2);
        iconBar.addView(barUnderline2);
        iconBox3.addView(ic3);
        iconBox3.addView(selectIc);
        iconBar.addView(iconBox3);
        iconBar.addView(barUnderline3);
        iconBox4.addView(ic4);
        iconBar.addView(iconBox4);
        mainLayer.addView(iconBar);
                //spotArea
        currentSpotBox.addView(currentSpot);
        spotArea.addView(currentSpotBox);
        spotArea.addView(spotContainer);
        mainLayer.addView(spotArea);
        bgcBox.addView(mainLayer);
            //finish
        container3.addView(bgcBox);
    }

    private float dpToPixel(float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
