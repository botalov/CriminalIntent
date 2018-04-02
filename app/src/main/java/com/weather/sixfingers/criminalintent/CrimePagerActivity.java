package com.weather.sixfingers.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;


public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    private Button mFirstButton;
    private Button mLastButton;

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        mFirstButton = findViewById(R.id.start_crime_button);
        mLastButton = findViewById(R.id.last_crime_button);

        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = findViewById(R.id.crime_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {

                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }


        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    mFirstButton.setEnabled(false);
                }
                else{
                    mFirstButton.setEnabled(true);
                }

                if(position == mCrimes.size() - 1){
                    mLastButton.setEnabled(false);
                }
                else{
                    mLastButton.setEnabled(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i<mCrimes.size(); i++){
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public void goToFirstCrime(View view){
        mViewPager.setCurrentItem(0);
    }

    public void goToLastCrime(View view){
        mViewPager.setCurrentItem(mCrimes.size() - 1);
    }
}
