package com.example.neck_is_turtle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class VideoFragment extends Fragment {

    static final int NUM_PAGES=2;
    ViewPager2 viewPager2;
    FragmentStateAdapter adapter;
    TabLayout tabs;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VideoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        viewPager2 = view.findViewById(R.id.viewpager);
        adapter = new ScreenSlidePagerAdapter(this);
        viewPager2.setAdapter(adapter);
        viewPager2.setSaveEnabled(false);

        tabs = view.findViewById(R.id.tabs);
        String[] tab_text = new String[]{"스트레칭", "운동"};
        new TabLayoutMediator(tabs, viewPager2,
                (tab, position) -> tab.setText(tab_text[position])
        ).attach();

        return view;
    }
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter{

        public ScreenSlidePagerAdapter(@NonNull VideoFragment fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if(position==0)
                return new StretchingFragment();
            else if(position==1)
                return new ExerciseFragment();
            return null;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}