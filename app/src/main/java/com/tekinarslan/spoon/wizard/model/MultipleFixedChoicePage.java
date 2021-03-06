package com.tekinarslan.spoon.wizard.model;

import android.support.v4.app.Fragment;
import com.tekinarslan.spoon.wizard.ui.MultipleChoiceFragment;

import java.util.ArrayList;

/** A page offering the user a number of non-mutually exclusive choices. */
public class MultipleFixedChoicePage extends SingleFixedChoicePage {
  public MultipleFixedChoicePage(com.tekinarslan.spoon.wizard.model.ModelCallbacks callbacks, String title) {
    super(callbacks, title);
  }

  @Override public Fragment createFragment() {
    return MultipleChoiceFragment.create(getKey());
  }

  @Override public void getReviewItems(ArrayList<ReviewItem> dest) {
    StringBuilder sb = new StringBuilder();

    ArrayList<String> selections = mData.getStringArrayList(Page.SIMPLE_DATA_KEY);
    if (selections != null && selections.size() > 0) {
      for (String selection : selections) {
        if (sb.length() > 0) {
          sb.append(", ");
        }
        sb.append(selection);
      }
    }

    dest.add(new ReviewItem(getTitle(), sb.toString(), getKey()));
  }

  @Override public boolean isCompleted() {
    ArrayList<String> selections = mData.getStringArrayList(Page.SIMPLE_DATA_KEY);
    return selections != null && selections.size() > 0;
  }
}
