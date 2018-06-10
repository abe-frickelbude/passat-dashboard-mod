
# Toolchains and workflows

## Board development

### Toolchain

* Corel Draw for drafting initial geometry and board dimensions/outlines
* KiCAD
  * Need to install the Sparkfun 3rd party libraries for e.g. WS2812 symbols/footprints
* Inkscape for aux work / final arrangement (see below why)

### Workflow

#### Corel

* Be sure to set all units to mm!
* Add small cross markers for precise placement of components in KiCAD 
* Export board outlines to DXF for import in Kicad
  * Make sure to also set units to mm during export
  * Make sure to select Autocad R12 or earlier as the format - later formats are not recognized by Kicad!  

### KiCAD

* When importing DXF board outlines, set the Layer to "Edge.Cuts", leave other settings as-is
* Be sure to remove any extraneous geometry (e.g. the aforementioned markers once they are no longer needed), as otherwise
  they will interfere with copper flooding etc!
* When declaring keep-out areas, do it on the corresponding copper layer, and make sure to tick the "No copper pour" checkbox
 to also restrict the polygon fill!
* The artwork required for UV exposure should be exported via "Plot", as follows:
  * Use SVG format
  * Only the copper layers
  * Set "Drill marks" to "small"

### Inkscape

* When import, do NOT resize the artwork in any way, as it already has the correct dimensions!