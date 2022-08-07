package org.example.core;

import com.google.inject.Singleton;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonListener;
import org.example.core.commands.ExamplePingCommand;
import org.example.core.listener.ExampleGameTickListener;

@Singleton
@AddonListener
public class ExampleAddon extends LabyAddon<ExampleConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.registerListener(ExampleGameTickListener.class);
    this.registerCommand(ExamplePingCommand.class);

    this.logger().info("Enabled the Addon");
  }

  @Override
  protected Class<ExampleConfiguration> configurationClass() {
    return ExampleConfiguration.class;
  }
}
