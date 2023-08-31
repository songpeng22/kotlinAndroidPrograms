// ILabelManager.aidl
package com.example.androidaidllibrary;

import com.example.androidaidllibrary.Label;

// Declare any non-default types here with import statements
interface ILabelManager {
    List<Label> getLabelList();
    void addLabel(in Label label);
}