// ILabelManager.aidl
package com.example.kotlinaidl;

import com.example.kotlinaidl.Label;
// Declare any non-default types here with import statements

interface ILabelManager {
    List<Label> getLabelList();
}