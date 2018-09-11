
# Design considerations and decisions

## Gauge backlights

## Gauge display backlights

## Center (Multifunction) Display backlight

* It has been experimentally determined that the MFD is a white-on-blue LCD. Apparently the brain-dead morons @VW/Bosch
  couldn't be arsed into using a display with neutral colors (e.g. usual white/transparent on gray), and 
  subsequently used red LEDs to back-light the display instead. The blue background acts as a filter, hence most of the
  background color is cancelled out while the text is red-tinted. 

* The best option here seems to be to provide a >cold< white backlight and optionally tint it with a blue component.
  This will result a "neutral", i.e. original white-on-blue image which will then hopefully harmonize with the other
  backlight colors.

* For practical reasons, the replacement backlight will be a light-guide (salvaged from an old TFT display) which
  is edge-lit by 3 to 4 WS2812s (making a replacement for the original whopping 15 LEDs proved too difficult, and it
  has been experimentally proven that the light-guide version provides sufficient brightness)
