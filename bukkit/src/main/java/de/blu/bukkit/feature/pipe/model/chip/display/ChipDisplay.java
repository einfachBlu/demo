package de.blu.bukkit.feature.pipe.model.chip.display;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter
@Setter
@AllArgsConstructor
public final class ChipDisplay {

  private Material material;
  private String displayName;
  private List<String> loreDescription;
}
