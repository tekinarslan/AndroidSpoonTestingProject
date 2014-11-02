package com.tekinarslan.spoon.wizard.model;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a wizard model, including the pages/steps in the wizard, their dependencies, and
 * their
 * currently populated choices/values/selections.
 *
 * To create an actual wizard model, extend this class and implement {@link #onNewRootPageList()}.
 */
public abstract class AbstractWizardModel implements com.tekinarslan.spoon.wizard.model.ModelCallbacks {
  protected Context mContext;

  private List<com.tekinarslan.spoon.wizard.model.ModelCallbacks> mListeners = new ArrayList<com.tekinarslan.spoon.wizard.model.ModelCallbacks>();
  private PageList mRootPageList;

  public AbstractWizardModel(Context context) {
    mRootPageList = onNewRootPageList();
    mContext = context;
  }

  /** Override this to define a new wizard model. */
  protected abstract com.tekinarslan.spoon.wizard.model.PageList onNewRootPageList();

  @Override public void onPageDataChanged(Page page) {
    // can't use for each because of concurrent modification (review fragment
    // can get added or removed and will register itself as a listener)
    for (int i = 0; i < mListeners.size(); i++) {
      mListeners.get(i).onPageDataChanged(page);
    }
  }

  @Override public void onPageTreeChanged() {
    // can't use for each because of concurrent modification (review fragment
    // can get added or removed and will register itself as a listener)
    for (int i = 0; i < mListeners.size(); i++) {
      mListeners.get(i).onPageTreeChanged();
    }
  }

  public Page findByKey(String key) {
    return mRootPageList.findByKey(key);
  }

  public void load(Bundle savedValues) {
    for (String key : savedValues.keySet()) {
      mRootPageList.findByKey(key).resetData(savedValues.getBundle(key));
    }
  }

  public void registerListener(com.tekinarslan.spoon.wizard.model.ModelCallbacks listener) {
    mListeners.add(listener);
  }

  public Bundle save() {
    Bundle bundle = new Bundle();
    for (Page page : getCurrentPageSequence()) {
      bundle.putBundle(page.getKey(), page.getData());
    }
    return bundle;
  }

  /**
   * Gets the current list of wizard steps, flattening nested (dependent) pages based on the
   * user's choices.
   */
  public List<Page> getCurrentPageSequence() {
    ArrayList<Page> flattened = new ArrayList<Page>();
    mRootPageList.flattenCurrentPageSequence(flattened);
    return flattened;
  }

  public void unregisterListener(com.tekinarslan.spoon.wizard.model.ModelCallbacks listener) {
    mListeners.remove(listener);
  }
}