package de.blu.common.attachment;

import java.util.HashMap;
import java.util.Map;

public abstract class SimpleAttachable<T extends Attachment> implements Attachable<T> {

  private final Map<Class<? extends Attachment>, Attachment> attachments = new HashMap<>();

  @Override
  public void attach(T attachment) {
    this.attachments.put(attachment.getClass(), attachment);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> A getAttachment(Class<? extends A> type) {
    final Attachment attachment = this.attachments.getOrDefault(type, null);
    if (attachment == null) {
      return null;
    }

    return (A) attachment;
  }

  @Override
  public <A> void removeAttachment(Class<? extends A> type) {
    this.attachments.remove(type);
  }

  @Override
  public <A> boolean hasAttachment(Class<? extends A> type) {
    return this.attachments.containsKey(type);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Map<Class<? extends T>, T> getAttachments() {
    Map<Class<? extends T>, T> attachments = new HashMap<>();

    for (Map.Entry<Class<? extends Attachment>, Attachment> entry : this.attachments.entrySet()) {
      attachments.put((Class<? extends T>) entry.getKey(), (T) entry.getValue());
    }

    return attachments;
  }
}
