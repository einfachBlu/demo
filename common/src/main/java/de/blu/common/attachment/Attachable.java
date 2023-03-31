package de.blu.common.attachment;

import java.util.Map;

public interface Attachable<T extends Attachment> {

  /**
   * Attach the given Attachment to access it later
   *
   * @param attachment the addon attachment for this instance
   */
  void attach(T attachment);

  /**
   * Get the Attachment by class
   *
   * @param type the class of the Attachment
   * @param <A>  the type of the Attachment
   * @return the Attachment if found or null
   */
  <A> A getAttachment(Class<? extends A> type);

  /**
   * Remove the Attachment by class
   *
   * @param type the class of the Attachment
   * @param <A>  the type of the Attachment
   */
  <A> void removeAttachment(Class<? extends A> type);

  /**
   * Get all Attachments
   *
   * @return all Attachments
   */
  Map<Class<? extends T>, T> getAttachments();

  /**
   * Check if the Attachable got an Attachment by class
   *
   * @param type the class of the Attachment
   * @param <A>  the type of the Attachment
   * @return true, if given Attachment was found by class or not.
   */
  <A> boolean hasAttachment(Class<? extends A> type);
}
