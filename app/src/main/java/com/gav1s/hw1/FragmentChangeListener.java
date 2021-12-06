package com.gav1s.hw1;

import androidx.fragment.app.Fragment;

public interface FragmentChangeListener
{
    void replaceFragment(Fragment fragment);
    void closeFragmentAndBackTo(int indexPopFragment);
    void changeIndex(int index);
}
