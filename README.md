# Resent-1.8


if you're reading this, that means one of the following:


* i open-sourced resent. this is most likely

* another dev was added to the project.

* you hacked into my account from my weak ass password and somehow bypassed github email verification.

&nbsp;
&nbsp;
&nbsp;

either way, here's some documentation on this project.
please note that I wrote all of this in a text editor so i probably made a few mistakes in the code.



## Module system
&nbsp;
&nbsp;
### Standard, non-render modules. (ex: fullbright, norain, tooltips)

create a new class structured like the following:


```
  public class ExampleModule extends Mod {
    public ExampleModule(){
      super("Example", Category.MISC); //use super("Example", Category,MISC, true); to add a gear icon
    }
    
    @Override //Overrides method in Mod class.
    public void onEnable(){
      //what happens when the module is enabled
    }
    
    @Override
    public void onDisable(){
      //what happens when the module is disabled
    }
  }
```    
&nbsp;
&nbsp;

### Render-related modules that requires drawing to the screen. (ex: keystrokes, info, fps, potionhud)


create a new class structured like this:

```
public class ExampleRenderModule extends RenderModule {
  public ExampleRenderModule(){
    super("ExampleRender", Category.MISC, 4 /*starting X position of the module*/, 4 /*starting Y position of the module*/); //again, add a ",true" to the end if you want a gear icon.
  }
  
  //this method is called in GuiIngame to draw objects to the screen.
  public void draw(){
    mc.fontRendererObj.drawString("Test", this.getX(), this.getY(), -1 /* -1 is the color of the text. */);
  }
  
  //you can still use onEnable and onDisable methods.
}
```

&nbsp;

## Settings

not too difficult to figure out on your own, here's an example:

```
public class ExampleRenderModule extends RenderModule {
  public ExampleRenderModule(){
    super("ExampleRender", Category.MISC, 4, 4, true);
    addSetting(example);
  }
  
  public static BooleanSetting example = new BooleanSetting("Example", "" /* description */, true /* default value */);
  
  public void draw(){
    if(example.getValue())
    mc.fontRendererObj.drawString("Test", this.getX(), this.getY(), -1 /* -1 is the color of the text. */);
  }
  
}
```

^ this example was done with a booleansetting, its similar to a modesetting, too lazy to write a documentation for that cope.

## Events

create a new class like so:

```
public class EventUpdate extends Event {

public boolean cancelled = false;

@Override
public boolean isCancelled(){
  return this.cancelled;
}

@Override
public void setCancelled(boolean cancelled){
  this.cancelled = canceled;
}
```

to hook the event, put this in:

&nbsp;

```
EventUpdate e = new EventUpdate();
Resent.onEvent(e);
```

