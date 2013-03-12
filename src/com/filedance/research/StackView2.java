package com.filedance.research;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StackView2 extends View {

	//Constants
	public final static int LAYER_COUNT = 4;		//Number of layers in library and stack
	public final static int CHOICE_COUNT = 8;		//Number of choices in library
	
	//Size of the overall graphic (Phrase + Image)
	private Bitmap mStackBitmap;		//Bitmap of canvas
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
	public StackView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
		adjustLayout();
	}
	
    public StackView2(Context context, AttributeSet attrs, int defStyle) {
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
    	mImageLibrary[0][0]= R.drawable.background00;
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
    	for (int layer = 0; layer < LAYER_COUNT-1; layer++) {
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
    
    public Bitmap alternative(Context context) {
    	//This is the Canvas we are going to draw in.
    	Canvas canvas = null;

//    	This is the layout we are going to use to create our view.
    	RelativeLayout relativeView = new RelativeLayout(context);

//    	This is the background we are going to set in our view, we get it from a resource file (R.drawable.blackBgrnd).
//    	The BitmapFactory.decodeResource method is used to get a resource image and convert it in a Bitmap (we will use the Bitmap class).
//    	The object mContext (context) must be passed from the Activity we are working on.
    	Bitmap viewBgrnd = BitmapFactory.decodeResource(context.getResources(), R.drawable.background00);

//    	We need another bitmap, to draw it on the canvas. We set the width and the height of this bitmap relative to the width and height we have created in our layout.
//    	Now this Bitmap is empty, but we are going to associate it with the canvas, so every time we draw in the canvas, it will be drawn in this bitmap object.
    	Bitmap returnedBitmap = Bitmap.createBitmap(relativeView.getWidth(), relativeView.getHeight(), Bitmap.Config.ARGB_8888);

//    	First of all, we had the canvas = null, now we create a Canvas object using the auxiliary bitmap we had created before.
    //	canvas = new Canvas(auxBitmap);
    	canvas = new Canvas(returnedBitmap);

//    	Now its time to create our view.
    	ImageView newImage = new ImageView(context);
    	
//    	We can add Images, for example:
    	Bitmap bitmapWithImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ecupcake_topping_03);
    	newImage.setImageBitmap(bitmapWithImage);

//    	We can set the imageView position in the view using “layout” method:
    	newImage.layout(0,0,100,100);

//    	    l Left position, relative to parent
//    	    t Top position, relative to parent
//    	    r Right position, relative to parent
//    	    b Bottom position, relative to parent

//    	and finally adding it to our layout:
    	relativeView.addView(newImage);

//    	or we can add text:
    	TextView newText = new TextView(context);
    	CharSequence newTextTitle = "“This is the text that is going to appear”";
    	newText.setText(newTextTitle);

//    	adding it to the layout in the same way:
    	relativeView.addView(newText);

//    	Once we have added all elements we want to our layout, we have to create a paint object:
    	Paint paint = new Paint();

//    	just to define default values of painting.
//    	We use the “drawBitmap” method from the canvas:
    	canvas.drawBitmap(viewBgrnd, 0, 0, paint);

//    	and finally we call dispatchDraw in the layout to draw the children views (imageView, textView) in the canvas.
    //	relativeView.dispatchDraw(canvas);
    	relativeView.draw(canvas);

//    	The returnedBitmap is the bitmap that contains the drawing of the views in the canvas, on it, we have the layout and its childrens as a Bitmap, after painting them in the Canvas.
//    	Conclusion

    	
//    	Summary:
//
//    	    Create empty bitmap. This bitmap will be the final bitmap with the views on it.
//    	    Create the canvas using that bitmap.
//    	    Create a layout and we add as many elements as we want.
//    	    Attach the layout to the canvas.
//    	    Because we have created the canvas using a bitmap object, all that is drawn in the canvas, will be drawn in the bitmap.

    	return returnedBitmap;
    }
    
    

}