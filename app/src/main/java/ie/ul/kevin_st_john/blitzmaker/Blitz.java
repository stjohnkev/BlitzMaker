package ie.ul.kevin_st_john.blitzmaker;

import java.util.Random;

public class Blitz {

    private String mName;
    private int mImageResourceId;
    private float mRating;
    private String mLocation;
    private Random random = new Random();

    public Blitz() {
      //  mName = getRandomFoodName();
     //   mImageResourceId = sFoodImageMap.get(mName);
        //mRating = 1.0f;

        mName="Blitz Name";
        mLocation="Blitz Location";
    }


    /*private String getRandomFoodName() {
        Object[] foods = Blitz.sFoodImageMap.keySet().toArray();
        return (String)foods[random.nextInt(foods.length)];
    }*/

    public String getName() {
        return mName;
    }

    public String getLocation(){return mLocation;}

    public float getmRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }
}
