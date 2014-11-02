package com.tekinarslan.spoon.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.tekinarslan.spoon.wizard.ui.SingleChoiceFragment;
import java.util.ArrayList;
import java.util.Arrays;

/** A page offering the user a number of mutually exclusive choices. */
public class SingleFixedChoicePage extends Page {
  protected ArrayList<String> mChoices = new ArrayList<String>();

  public SingleFixedChoicePage(com.tekinarslan.spoon.wizard.model.ModelCallbacks callbacks, String title) {
    super(callbacks, title);
  }

  @Override
  public Fragment createFragment() {
    return SingleChoiceFragment.create(getKey());
  }

  public String getOptionAt(int position) {
    return mChoices.get(position);
  }

  public int getOptionCount() {
    return mChoices.size();
  }

  @Override public void getReviewItems(ArrayList<com.tekinarslan.spoon.wizard.model.ReviewItem> dest) {
    dest.add(new com.tekinarslan.spoon.wizard.model.ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY), getKey()));
  }

  @Override public boolean isCompleted() {
    return !TextUtils.isEmpty(mData.getString(SIMPLE_DATA_KEY));
  }

  public SingleFixedChoicePage setChoices(String... choices) {
    mChoices.addAll(Arrays.asList(choices));
    return this;
  }

  public SingleFixedChoicePage setValue(String value) {
    mData.putString(SIMPLE_DATA_KEY, value);
    return this;
  }
}
