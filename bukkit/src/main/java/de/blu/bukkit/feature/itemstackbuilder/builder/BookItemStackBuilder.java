package de.blu.bukkit.feature.itemstackbuilder.builder;

import org.bukkit.Material;
import org.bukkit.inventory.meta.BookMeta;

public final class BookItemStackBuilder extends ItemStackBuilder {

  public BookItemStackBuilder() {
    super();
    this.type(Material.WRITTEN_BOOK);
  }

  public static BookItemStackBuilder of(ItemStackBuilder itemStackBuilder) {
    itemStackBuilder.build();
    BookItemStackBuilder bookItemStackBuilder = new BookItemStackBuilder();
    bookItemStackBuilder.itemStack = itemStackBuilder.itemStack;
    bookItemStackBuilder.itemMeta = itemStackBuilder.itemMeta;
    return bookItemStackBuilder;
  }

  public boolean writeable() {
    return this.type().equals(Material.LEGACY_BOOK_AND_QUILL);
  }

  public BookItemStackBuilder writeable(boolean value) {
    BookMeta bookMeta = (BookMeta) this.getItemMeta();
    if (value) {
      this.type(Material.LEGACY_BOOK_AND_QUILL);
    } else {
      this.type(Material.WRITTEN_BOOK);
    }
    this.setItemMeta(bookMeta);

    return this;
  }

  public BookItemStackBuilder title(String title) {
    BookMeta bookMeta = (BookMeta) this.getItemMeta();
    bookMeta.setTitle(title);

    return this;
  }

  public BookItemStackBuilder author(String author) {
    BookMeta bookMeta = (BookMeta) this.getItemMeta();
    bookMeta.setAuthor(author);

    return this;
  }

  public BookItemStackBuilder addPage(String... data) {
    BookMeta bookMeta = (BookMeta) this.getItemMeta();
    bookMeta.addPage(data);

    return this;
  }

  public BookItemStackBuilder setPage(int i, String data) {
    BookMeta bookMeta = (BookMeta) this.getItemMeta();

    if (bookMeta.getPageCount() <= i) {
      for (int i2 = 0; i2 < i; i2++) {
        if (bookMeta.getPageCount() <= i2) {
          this.addPage(" ");
        }
      }
    }

    bookMeta.setPage(i, data);
    return this;
  }

  public BookItemStackBuilder setPages(String... data) {
    BookMeta bookMeta = (BookMeta) this.getItemMeta();
    bookMeta.setPages(data);

    return this;
  }
}
