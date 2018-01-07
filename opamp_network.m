
## As described in TI SLOA097 application note
1;

function [m,b] = mb(Vin_min, Vin_max, Vout_min, Vout_max)
  m = (Vout_max - Vout_min) / (Vin_max - Vin_min)
  b = Vout_min - m * Vin_min
endfunction

# Main entry point
function calculateValues() 
  Vin_min = input("Pick Vin_min: ")
  Vin_max = input("Pick Vin_max: ")
  Vout_min = input("Pick Vout_min: ")
  Vout_max = input("Pick Vout_max: ")
  
  # calculate slope and intercept
  [m,b] = mb(Vin_min, Vin_max, Vout_min, Vout_max)
  
  if(m > 0.0 && b > 0.0) 
     printf("case 1: positive m, positive b\n")
  elseif(m > 0.0 && b < 0.0)
    printf("case 2: positive m, negative b\n")
  elseif(m < 0.0 && b > 0.0)
    printf("case 3: negative m, positive b\n")
  else # m and b both < 0.0
    printf("case 4: negative m, negative b\n")
  endif
   
endfunction

function app()  
  
  ## UI style constants
  panelBackground = [1.0, 1.0, 0.8];
  panelForeground = [0.0, 0.0, 0.0];
  controlBackground = [1.0, 1.0, 0.8];
  controlForeground = [0.0, 0.0, 0.0];
  
  f = figure("name", "Gain / dc offset calculator", "position", [300,200, 800,600])
  
  # Voltages
  vPanel = uipanel(f, "title", "In/Out voltage ranges", 
                   "position", [0.01,0.70, 0.25, 0.25], 
                   "backgroundcolor", panelBackground,
                   "foregroundcolor", panelForeground)
  
  VinMinLabel = uicontrol(vPanel, "style", "text", "string", "Vin_min", 
                          "position", [10,100, 80, 20], 
                          "backgroundcolor", controlBackground,
                          "foregroundcolor", controlForeground)
  
  VinMinInput = uicontrol(vPanel, "style", "edit", "string", "0.0", 
                          "position", [100, 100, 80, 20],
                          "backgroundcolor", controlBackground,
                          "foregroundcolor", controlForeground)
  
  VinMaxLabel = uicontrol(vPanel, "style", "text", "string", "Vin_max", 
                          "position", [10, 70, 80, 20], 
                          "backgroundcolor", controlBackground,
                          "foregroundcolor", controlForeground)
  
  VinMaxInput = uicontrol(vPanel, "style", "edit", "string", "0.0", 
                          "position", [100, 70, 80, 20],
                          "backgroundcolor", controlBackground,
                          "foregroundcolor", controlForeground)                       
                          
                          
  VoutMinLabel = uicontrol(vPanel, "style", "text", "string", "Vout_min", 
                            "position", [10,40, 80, 20], 
                            "backgroundcolor", controlBackground,
                            "foregroundcolor", controlForeground)
  
  VoutMinInput = uicontrol(vPanel, "style", "edit", "string", "0.0", 
                          "position", [100, 40, 80, 20],
                          "backgroundcolor", controlBackground,
                          "foregroundcolor", controlForeground)
  
  VoutMaxLabel = uicontrol(vPanel, "style", "text", "string", "Vout_max", 
                          "position", [10,10, 80, 20], 
                          "backgroundcolor", controlBackground,
                          "foregroundcolor", controlForeground)
  
  VoutMaxInput = uicontrol(vPanel, "style", "edit", "string", "0.0", 
                          "position", [100, 10, 80, 20],
                          "backgroundcolor", controlBackground,
                          "foregroundcolor", controlForeground)
 
endfunction