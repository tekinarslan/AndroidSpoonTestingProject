package com.tekinarslan.spoon.wizard.model;

import java.util.ArrayList;

/** Represents a node in the page tree. Can either be a single page, or a page container. */
public interface PageTreeNode {
  Page findByKey(String key);
  void flattenCurrentPageSequence(ArrayList<Page> dest);
}
