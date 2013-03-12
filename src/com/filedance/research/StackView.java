package com.filedance.research;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class StackView extends View {

	//Constants
	public final static int LAYER_COUNT = 4;		//Number of layers in library and stack
	public final static int CHOICE_COUNT = 8;		//Number of choices in library
	
	//Size of the overall graphic (Phrase + Image)
	private int mStackWidth;			//View width
	private int mStackHeight;			//View Height (Phrase + Image)
	
    //Phrase
    private boolean mPhraseShow;       //Hide or show phrase
    private float mPhrasePortion;	   //Portion = % of mStackHeight
    private CharSequence mPhraseText;  //The phrase text
    private int mPhraseColor;          //Color of the phrase text
    private final Paint mPhrasePaint = new Paint(Paint.ANTI_ALIAS_FLAG);    //Phrase paint brush
    
    //Stack of Images
	private int[][] mImageLibrary= new int[LAYER_COUNT][CHOICE_COUNT];   	//Array stores resource ID's (layer)(choice)
	private Drawable[] mImageStack = new Drawable[LAYER_COUNT];   //Array stores image stack (layer)
	private Rect mImageRect = new Rect();				//Stored image bounds

    
    //Public Contructors   
	public StackView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
		adjustLayout();
	}
	
    public StackView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
		adjustLayout();
	}

    //Set initial defaults upon view creation
    private void init(Context context, AttributeSet attrs) {
    	//Phrase Defaults
        mPhraseShow = true;
        mPhraseText = "Hello Cupcake World!";
        mPhraseColor = Color.BLUE;
        mPhrasePortion = 10; //Percent
    	
        //Image Library
    	mImageLibrary[0][0]= R.drawable.ecupcake_cake_00;
    	mImageLibrary[0][1]= R.drawable.background01;
    	mImageLibrary[0][2]= R.drawable.background02;    	
    	mImageLibrary[0][3]= R.drawable.background03;
    	mImageLibrary[0][4]= R.drawable.background00;
    	mImageLibrary[0][5]= R.drawable.background01;
    	mImageLibrary[0][6]= R.drawable.background02;    	
    	mImageLibrary[0][7]= R.drawable.background03;   

    	mImageLibrary[1][0]= R.drawable.ecupcake_cake_00;
    	mImageLibrary[1][1]= R.drawable.ecupcake_cake_01;
    	mImageLibrary[1][2]= R.drawable.ecupcake_cake_02;    	
    	mImageLibrary[1][3]= R.drawable.ecupcake_cake_03;
    	mImageLibrary[1][4]= R.drawable.ecupcake_cake_00;
    	mImageLibrary[1][5]= R.drawable.ecupcake_cake_01;
    	mImageLibrary[1][6]= R.drawable.ecupcake_cake_02;    	
    	mImageLibrary[1][7]= R.drawable.ecupcake_cake_03;   
    	
    	mImageLibrary[2][0]= R.drawable.ecupcake_frosting_00;
    	mImageLibrary[2][1]= R.drawable.ecupcake_frosting_01;
    	mImageLibrary[2][2]= R.drawable.ecupcake_frosting_02;    	
    	mImageLibrary[2][3]= R.drawable.ecupcake_frosting_03;
    	mImageLibrary[2][4]= R.drawable.ecupcake_frosting_00;
    	mImageLibrary[2][5]= R.drawable.ecupcake_frosting_01;
    	mImageLibrary[2][6]= R.drawable.ecupcake_frosting_02;    	
    	mImageLibrary[2][7]= R.drawable.ecupcake_frosting_03;  
    	
    	mImageLibrary[3][0]= R.drawable.ecupcake_topping_00;
    	mImageLibrary[3][1]= R.drawable.ecupcake_topping_01;
    	mImageLibrary[3][2]= R.drawable.ecupcake_topping_02;    	
    	mImageLibrary[3][3]= R.drawable.ecupcake_topping_03;
    	mImageLibrary[3][4]= R.drawable.ecupcake_topping_00;
    	mImageLibrary[3][5]= R.drawable.ecupcake_topping_01;
    	mImageLibrary[3][6]= R.drawable.ecupcake_topping_02;    	
    	mImageLibrary[3][7]= R.drawable.ecupcake_topping_03; 
    	
    	//Image Defaults (choice=0)
    	for (int layer = 0; layer < LAYER_COUNT; layer++) {
    		mImageStack[layer] = getResources().getDrawable(mImageLibrary[layer][0]);    	
    	}
    	
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
    	super.onSizeChanged(w, h, oldw, oldh);
    
    	mStackWidth = w;
    	mStackHeight = h;
    	mImageRect.set(0, (int) ((mStackHeight/mPhrasePortion) + 5), w, h);
    	adjustLayout();
  //      Toast.makeText(this.getContext(), "w=" + w + "h=" + h + "oldw=" + oldw + "oldh=" + oldh, Toast.LENGTH_LONG).show();
    }
    
    
    //Recalculate location, size, and color when parameters change
    private void adjustLayout() {
    	if (mPhraseShow) {
		    mPhrasePaint.setTextSize(mStackHeight/mPhrasePortion);	//Phrase height is a % of mStackSize
		    mPhrasePaint.setTextAlign(Paint.Align.CENTER);					//Center alignment
		    mPhrasePaint.setColor(mPhraseColor);							//Text color
	//	    mPhrasePaint.setAlpha(100);  //The transparency of the text 255=opaque, 0 = invisible
    	}

    	//Image sizes
    	for (int layer = 0; layer < LAYER_COUNT; layer++) {
    		mImageStack[layer].setBounds(mImageRect);						//Reset the size of each image
    	}
    	
    	invalidate();
    	requestLayout();
    }
    
	@Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //This routine is called a lot, so keep the code simple and fast
        
        
        
        //Draw images on top of each other, starting at zero
    	for (int layer = 1; layer < LAYER_COUNT; layer++) {
    		mImageStack[layer].draw(canvas);		//Draw each image on the canvas
    	}        

    	if (mPhraseShow) {
    		//Draw text phrase, centered, gapped down from the top
    		canvas.drawText(mPhraseText.toString(), mStackWidth/2, mStackHeight/mPhrasePortion, mPhrasePaint);
    	}
   	}

	//mPhraseShow
	public boolean isPhraseShow() {
	   return mPhraseShow;
	}
	public void setPhraseShow(boolean phraseShow) {
	   mPhraseShow = phraseShow;
	   adjustLayout();
	}
	
	//mPhraseText
	public CharSequence getPhraseText() {
	   return mPhraseText;
	}
	public void setPhraseText(CharSequence phraseText) {
	   mPhraseText = phraseText;
	   adjustLayout();
	}
	
	//mPhraseColor
	public int getPhraseColor() {
	   return mPhraseColor;
	}
	public void setPhraseColor(int phraseColor) {
	   mPhraseColor = phraseColor;
	   adjustLayout();
	}
	
	//mImageStack
    public Drawable getImage(int layer, int choice) {
    	return getResources().getDrawable(mImageLibrary[layer][choice]);
    }
    public void setImage(int layer, int choice) {
    	mImageStack[layer] = getResources().getDrawable(mImageLibrary[layer][choice]);
 	   adjustLayout();
    }
   
}