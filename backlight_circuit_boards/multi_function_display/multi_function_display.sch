EESchema Schematic File Version 4
LIBS:multi_function_display-cache
EELAYER 26 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L power:VCC #PWR03
U 1 1 5B734EBF
P 700 6375
F 0 "#PWR03" H 700 6225 50  0001 C CNN
F 1 "VCC" H 717 6548 50  0000 C CNN
F 2 "" H 700 6375 50  0001 C CNN
F 3 "" H 700 6375 50  0001 C CNN
	1    700  6375
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR04
U 1 1 5B734F11
P 700 7075
F 0 "#PWR04" H 700 6825 50  0001 C CNN
F 1 "GND" H 705 6902 50  0000 C CNN
F 2 "" H 700 7075 50  0001 C CNN
F 3 "" H 700 7075 50  0001 C CNN
	1    700  7075
	1    0    0    -1  
$EndComp
$Comp
L Device:C C1
U 1 1 5B734F41
P 700 6725
F 0 "C1" H 720 6805 50  0000 L CNN
F 1 "0.1" H 720 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 738 6575 50  0001 C CNN
F 3 "~" H 700 6725 50  0001 C CNN
	1    700  6725
	1    0    0    -1  
$EndComp
Wire Wire Line
	700  6375 700  6575
Wire Wire Line
	700  6875 700  7075
$Comp
L power:VCC #PWR05
U 1 1 5B7351EB
P 975 6375
F 0 "#PWR05" H 975 6225 50  0001 C CNN
F 1 "VCC" H 992 6548 50  0000 C CNN
F 2 "" H 975 6375 50  0001 C CNN
F 3 "" H 975 6375 50  0001 C CNN
	1    975  6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C2
U 1 1 5B7351F8
P 975 6725
F 0 "C2" H 995 6805 50  0000 L CNN
F 1 "0.1" H 995 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 1013 6575 50  0001 C CNN
F 3 "~" H 975 6725 50  0001 C CNN
	1    975  6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR06
U 1 1 5B735220
P 975 7075
F 0 "#PWR06" H 975 6825 50  0001 C CNN
F 1 "GND" H 980 6902 50  0000 C CNN
F 2 "" H 975 7075 50  0001 C CNN
F 3 "" H 975 7075 50  0001 C CNN
	1    975  7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	975  6375 975  6575
Wire Wire Line
	975  6875 975  7075
$Comp
L power:VCC #PWR07
U 1 1 5B73525B
P 1225 6375
F 0 "#PWR07" H 1225 6225 50  0001 C CNN
F 1 "VCC" H 1242 6548 50  0000 C CNN
F 2 "" H 1225 6375 50  0001 C CNN
F 3 "" H 1225 6375 50  0001 C CNN
	1    1225 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C3
U 1 1 5B73526A
P 1225 6725
F 0 "C3" H 1245 6805 50  0000 L CNN
F 1 "0.1" H 1245 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 1263 6575 50  0001 C CNN
F 3 "~" H 1225 6725 50  0001 C CNN
	1    1225 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR08
U 1 1 5B735292
P 1225 7075
F 0 "#PWR08" H 1225 6825 50  0001 C CNN
F 1 "GND" H 1230 6902 50  0000 C CNN
F 2 "" H 1225 7075 50  0001 C CNN
F 3 "" H 1225 7075 50  0001 C CNN
	1    1225 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	1225 6375 1225 6575
Wire Wire Line
	1225 6875 1225 7075
$Comp
L power:PWR_FLAG #FLG01
U 1 1 5B735374
P 5800 6350
F 0 "#FLG01" H 5800 6425 50  0001 C CNN
F 1 "PWR_FLAG" H 5800 6524 50  0000 C CNN
F 2 "" H 5800 6350 50  0001 C CNN
F 3 "~" H 5800 6350 50  0001 C CNN
	1    5800 6350
	1    0    0    -1  
$EndComp
$Comp
L power:PWR_FLAG #FLG02
U 1 1 5B7353C7
P 6375 6350
F 0 "#FLG02" H 6375 6425 50  0001 C CNN
F 1 "PWR_FLAG" H 6375 6524 50  0000 C CNN
F 2 "" H 6375 6350 50  0001 C CNN
F 3 "~" H 6375 6350 50  0001 C CNN
	1    6375 6350
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR011
U 1 1 5B7353F0
P 5800 7050
F 0 "#PWR011" H 5800 6800 50  0001 C CNN
F 1 "GND" H 5805 6877 50  0000 C CNN
F 2 "" H 5800 7050 50  0001 C CNN
F 3 "" H 5800 7050 50  0001 C CNN
	1    5800 7050
	1    0    0    -1  
$EndComp
$Comp
L power:VCC #PWR012
U 1 1 5B735401
P 6375 7050
F 0 "#PWR012" H 6375 6900 50  0001 C CNN
F 1 "VCC" H 6393 7223 50  0000 C CNN
F 2 "" H 6375 7050 50  0001 C CNN
F 3 "" H 6375 7050 50  0001 C CNN
	1    6375 7050
	-1   0    0    1   
$EndComp
Wire Wire Line
	5800 6350 5800 7050
Wire Wire Line
	6375 6350 6375 7050
$Comp
L SparkFun-LED:WS2812B D1
U 1 1 5B7355C6
P 2525 2100
F 0 "D1" H 2525 2660 45  0000 C CNN
F 1 "WS2812B" H 2525 2576 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 2525 2550 20  0001 C CNN
F 3 "" H 2525 2100 50  0001 C CNN
F 4 "DIO-12503" H 2525 2481 60  0000 C CNN "Field4"
	1    2525 2100
	1    0    0    -1  
$EndComp
$Comp
L SparkFun-LED:WS2812B D2
U 1 1 5B7356F7
P 4000 2100
F 0 "D2" H 4000 2660 45  0000 C CNN
F 1 "WS2812B" H 4000 2576 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 4000 2550 20  0001 C CNN
F 3 "" H 4000 2100 50  0001 C CNN
F 4 "DIO-12503" H 4000 2481 60  0000 C CNN "Field4"
	1    4000 2100
	1    0    0    -1  
$EndComp
$Comp
L SparkFun-LED:WS2812B D3
U 1 1 5B73578E
P 5625 2100
F 0 "D3" H 5625 2660 45  0000 C CNN
F 1 "WS2812B" H 5625 2576 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 5625 2550 20  0001 C CNN
F 3 "" H 5625 2100 50  0001 C CNN
F 4 "DIO-12503" H 5625 2481 60  0000 C CNN "Field4"
	1    5625 2100
	1    0    0    -1  
$EndComp
$Comp
L power:VCC #PWR01
U 1 1 5B735983
P 1300 1075
F 0 "#PWR01" H 1300 925 50  0001 C CNN
F 1 "VCC" H 1317 1248 50  0000 C CNN
F 2 "" H 1300 1075 50  0001 C CNN
F 3 "" H 1300 1075 50  0001 C CNN
	1    1300 1075
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR02
U 1 1 5B73599A
P 1300 1575
F 0 "#PWR02" H 1300 1325 50  0001 C CNN
F 1 "GND" H 1305 1402 50  0000 C CNN
F 2 "" H 1300 1575 50  0001 C CNN
F 3 "" H 1300 1575 50  0001 C CNN
	1    1300 1575
	1    0    0    -1  
$EndComp
$Comp
L power:VCC #PWR09
U 1 1 5B7359B1
P 9800 1050
F 0 "#PWR09" H 9800 900 50  0001 C CNN
F 1 "VCC" H 9817 1223 50  0000 C CNN
F 2 "" H 9800 1050 50  0001 C CNN
F 3 "" H 9800 1050 50  0001 C CNN
	1    9800 1050
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR010
U 1 1 5B7359C8
P 9800 1400
F 0 "#PWR010" H 9800 1150 50  0001 C CNN
F 1 "GND" H 9805 1227 50  0000 C CNN
F 2 "" H 9800 1400 50  0001 C CNN
F 3 "" H 9800 1400 50  0001 C CNN
	1    9800 1400
	1    0    0    -1  
$EndComp
$Comp
L Connector_Generic:Conn_01x03 J1
U 1 1 5B92D54A
P 900 1325
F 0 "J1" H 820 1000 50  0000 C CNN
F 1 "Conn_01x03" H 820 1091 50  0000 C CNN
F 2 "Frickelbude Connectors:wire_solder_pads_1x03_2.5x1.7mm_Pitch2.54mm" H 900 1325 50  0001 C CNN
F 3 "~" H 900 1325 50  0001 C CNN
	1    900  1325
	-1   0    0    1   
$EndComp
$Comp
L Connector_Generic:Conn_01x03 J2
U 1 1 5B92D7F9
P 10150 1225
F 0 "J2" H 10230 1267 50  0000 L CNN
F 1 "Conn_01x03" H 10230 1176 50  0000 L CNN
F 2 "Frickelbude Connectors:wire_solder_pads_1x03_2.5x1.7mm_Pitch2.54mm" H 10150 1225 50  0001 C CNN
F 3 "~" H 10150 1225 50  0001 C CNN
	1    10150 1225
	1    0    0    -1  
$EndComp
Wire Wire Line
	1100 1425 1300 1425
Wire Wire Line
	1300 1425 1300 1575
Wire Wire Line
	1100 1225 1300 1225
Wire Wire Line
	1300 1225 1300 1075
Wire Wire Line
	9950 1125 9800 1125
Wire Wire Line
	9800 1125 9800 1050
$Comp
L power:GND #PWR014
U 1 1 5B92E4B6
P 1900 2300
F 0 "#PWR014" H 1900 2050 50  0001 C CNN
F 1 "GND" H 1905 2127 50  0000 C CNN
F 2 "" H 1900 2300 50  0001 C CNN
F 3 "" H 1900 2300 50  0001 C CNN
	1    1900 2300
	1    0    0    -1  
$EndComp
Wire Wire Line
	1900 2300 1900 2200
Wire Wire Line
	1900 2200 1975 2200
$Comp
L power:GND #PWR016
U 1 1 5B92E608
P 3375 2325
F 0 "#PWR016" H 3375 2075 50  0001 C CNN
F 1 "GND" H 3380 2152 50  0000 C CNN
F 2 "" H 3375 2325 50  0001 C CNN
F 3 "" H 3375 2325 50  0001 C CNN
	1    3375 2325
	1    0    0    -1  
$EndComp
Wire Wire Line
	3375 2325 3375 2200
Wire Wire Line
	3375 2200 3450 2200
$Comp
L power:GND #PWR018
U 1 1 5B92E7C3
P 4925 2325
F 0 "#PWR018" H 4925 2075 50  0001 C CNN
F 1 "GND" H 4930 2152 50  0000 C CNN
F 2 "" H 4925 2325 50  0001 C CNN
F 3 "" H 4925 2325 50  0001 C CNN
	1    4925 2325
	1    0    0    -1  
$EndComp
Wire Wire Line
	5075 2200 4925 2200
Wire Wire Line
	4925 2200 4925 2325
$Comp
L power:VCC #PWR013
U 1 1 5B92EA16
P 1900 1750
F 0 "#PWR013" H 1900 1600 50  0001 C CNN
F 1 "VCC" H 1917 1923 50  0000 C CNN
F 2 "" H 1900 1750 50  0001 C CNN
F 3 "" H 1900 1750 50  0001 C CNN
	1    1900 1750
	1    0    0    -1  
$EndComp
Wire Wire Line
	1900 1750 1900 1900
Wire Wire Line
	1900 1900 1975 1900
$Comp
L power:VCC #PWR015
U 1 1 5B92EC57
P 3375 1750
F 0 "#PWR015" H 3375 1600 50  0001 C CNN
F 1 "VCC" H 3392 1923 50  0000 C CNN
F 2 "" H 3375 1750 50  0001 C CNN
F 3 "" H 3375 1750 50  0001 C CNN
	1    3375 1750
	1    0    0    -1  
$EndComp
Wire Wire Line
	3375 1750 3375 1900
Wire Wire Line
	3375 1900 3450 1900
$Comp
L power:VCC #PWR017
U 1 1 5B92F73A
P 4925 1725
F 0 "#PWR017" H 4925 1575 50  0001 C CNN
F 1 "VCC" H 4942 1898 50  0000 C CNN
F 2 "" H 4925 1725 50  0001 C CNN
F 3 "" H 4925 1725 50  0001 C CNN
	1    4925 1725
	1    0    0    -1  
$EndComp
Wire Wire Line
	4925 1725 4925 1900
Wire Wire Line
	4925 1900 5075 1900
Wire Wire Line
	3175 1900 3075 1900
Wire Wire Line
	3075 2200 3175 2200
Wire Wire Line
	3175 2200 3175 2625
Wire Wire Line
	3175 2625 4600 2625
Wire Wire Line
	4600 2625 4600 1900
Wire Wire Line
	4600 1900 4550 1900
Wire Wire Line
	4550 2200 4675 2200
Wire Wire Line
	4675 2200 4675 2625
Wire Wire Line
	4675 2625 6250 2625
Wire Wire Line
	6250 2625 6250 1900
Wire Wire Line
	6250 1900 6175 1900
$Comp
L SparkFun-LED:WS2812B D5
U 1 1 5B958053
P 8775 2100
F 0 "D5" H 8775 2660 45  0000 C CNN
F 1 "WS2812B" H 8775 2576 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 8775 2550 20  0001 C CNN
F 3 "" H 8775 2100 50  0001 C CNN
F 4 "DIO-12503" H 8775 2481 60  0000 C CNN "Field4"
	1    8775 2100
	1    0    0    -1  
$EndComp
$Comp
L power:VCC #PWR019
U 1 1 5B95957F
P 1475 6375
F 0 "#PWR019" H 1475 6225 50  0001 C CNN
F 1 "VCC" H 1492 6548 50  0000 C CNN
F 2 "" H 1475 6375 50  0001 C CNN
F 3 "" H 1475 6375 50  0001 C CNN
	1    1475 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C4
U 1 1 5B959585
P 1475 6725
F 0 "C4" H 1495 6805 50  0000 L CNN
F 1 "0.1" H 1495 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 1513 6575 50  0001 C CNN
F 3 "~" H 1475 6725 50  0001 C CNN
	1    1475 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR020
U 1 1 5B95958C
P 1475 7075
F 0 "#PWR020" H 1475 6825 50  0001 C CNN
F 1 "GND" H 1480 6902 50  0000 C CNN
F 2 "" H 1475 7075 50  0001 C CNN
F 3 "" H 1475 7075 50  0001 C CNN
	1    1475 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	1475 6375 1475 6575
Wire Wire Line
	1475 6875 1475 7075
$Comp
L power:VCC #PWR021
U 1 1 5B959C86
P 1725 6375
F 0 "#PWR021" H 1725 6225 50  0001 C CNN
F 1 "VCC" H 1742 6548 50  0000 C CNN
F 2 "" H 1725 6375 50  0001 C CNN
F 3 "" H 1725 6375 50  0001 C CNN
	1    1725 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C5
U 1 1 5B959C8C
P 1725 6725
F 0 "C5" H 1745 6805 50  0000 L CNN
F 1 "0.1" H 1745 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 1763 6575 50  0001 C CNN
F 3 "~" H 1725 6725 50  0001 C CNN
	1    1725 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR022
U 1 1 5B959C93
P 1725 7075
F 0 "#PWR022" H 1725 6825 50  0001 C CNN
F 1 "GND" H 1730 6902 50  0000 C CNN
F 2 "" H 1725 7075 50  0001 C CNN
F 3 "" H 1725 7075 50  0001 C CNN
	1    1725 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	1725 6375 1725 6575
Wire Wire Line
	1725 6875 1725 7075
$Comp
L power:VCC #PWR023
U 1 1 5B95A479
P 1975 6375
F 0 "#PWR023" H 1975 6225 50  0001 C CNN
F 1 "VCC" H 1992 6548 50  0000 C CNN
F 2 "" H 1975 6375 50  0001 C CNN
F 3 "" H 1975 6375 50  0001 C CNN
	1    1975 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C6
U 1 1 5B95A47F
P 1975 6725
F 0 "C6" H 1995 6805 50  0000 L CNN
F 1 "0.1" H 1995 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 2013 6575 50  0001 C CNN
F 3 "~" H 1975 6725 50  0001 C CNN
	1    1975 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR024
U 1 1 5B95A486
P 1975 7075
F 0 "#PWR024" H 1975 6825 50  0001 C CNN
F 1 "GND" H 1980 6902 50  0000 C CNN
F 2 "" H 1975 7075 50  0001 C CNN
F 3 "" H 1975 7075 50  0001 C CNN
	1    1975 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	1975 6375 1975 6575
Wire Wire Line
	1975 6875 1975 7075
$Comp
L power:VCC #PWR025
U 1 1 5B95AD66
P 2225 6375
F 0 "#PWR025" H 2225 6225 50  0001 C CNN
F 1 "VCC" H 2242 6548 50  0000 C CNN
F 2 "" H 2225 6375 50  0001 C CNN
F 3 "" H 2225 6375 50  0001 C CNN
	1    2225 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C7
U 1 1 5B95AD6C
P 2225 6725
F 0 "C7" H 2245 6805 50  0000 L CNN
F 1 "0.1" H 2245 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 2263 6575 50  0001 C CNN
F 3 "~" H 2225 6725 50  0001 C CNN
	1    2225 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR026
U 1 1 5B95AD73
P 2225 7075
F 0 "#PWR026" H 2225 6825 50  0001 C CNN
F 1 "GND" H 2230 6902 50  0000 C CNN
F 2 "" H 2225 7075 50  0001 C CNN
F 3 "" H 2225 7075 50  0001 C CNN
	1    2225 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	2225 6375 2225 6575
Wire Wire Line
	2225 6875 2225 7075
$Comp
L power:VCC #PWR027
U 1 1 5B95B74D
P 2475 6375
F 0 "#PWR027" H 2475 6225 50  0001 C CNN
F 1 "VCC" H 2492 6548 50  0000 C CNN
F 2 "" H 2475 6375 50  0001 C CNN
F 3 "" H 2475 6375 50  0001 C CNN
	1    2475 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C8
U 1 1 5B95B753
P 2475 6725
F 0 "C8" H 2495 6805 50  0000 L CNN
F 1 "0.1" H 2495 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 2513 6575 50  0001 C CNN
F 3 "~" H 2475 6725 50  0001 C CNN
	1    2475 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR028
U 1 1 5B95B75A
P 2475 7075
F 0 "#PWR028" H 2475 6825 50  0001 C CNN
F 1 "GND" H 2480 6902 50  0000 C CNN
F 2 "" H 2475 7075 50  0001 C CNN
F 3 "" H 2475 7075 50  0001 C CNN
	1    2475 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	2475 6375 2475 6575
Wire Wire Line
	2475 6875 2475 7075
$Comp
L power:VCC #PWR029
U 1 1 5B95C246
P 2725 6375
F 0 "#PWR029" H 2725 6225 50  0001 C CNN
F 1 "VCC" H 2742 6548 50  0000 C CNN
F 2 "" H 2725 6375 50  0001 C CNN
F 3 "" H 2725 6375 50  0001 C CNN
	1    2725 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C9
U 1 1 5B95C24C
P 2725 6725
F 0 "C9" H 2745 6805 50  0000 L CNN
F 1 "0.1" H 2745 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 2763 6575 50  0001 C CNN
F 3 "~" H 2725 6725 50  0001 C CNN
	1    2725 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR030
U 1 1 5B95C253
P 2725 7075
F 0 "#PWR030" H 2725 6825 50  0001 C CNN
F 1 "GND" H 2730 6902 50  0000 C CNN
F 2 "" H 2725 7075 50  0001 C CNN
F 3 "" H 2725 7075 50  0001 C CNN
	1    2725 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	2725 6375 2725 6575
Wire Wire Line
	2725 6875 2725 7075
$Comp
L power:VCC #PWR031
U 1 1 5B95CE4F
P 2975 6375
F 0 "#PWR031" H 2975 6225 50  0001 C CNN
F 1 "VCC" H 2992 6548 50  0000 C CNN
F 2 "" H 2975 6375 50  0001 C CNN
F 3 "" H 2975 6375 50  0001 C CNN
	1    2975 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C10
U 1 1 5B95CE55
P 2975 6725
F 0 "C10" H 2995 6805 50  0000 L CNN
F 1 "0.1" H 2995 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 3013 6575 50  0001 C CNN
F 3 "~" H 2975 6725 50  0001 C CNN
	1    2975 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR032
U 1 1 5B95CE5C
P 2975 7075
F 0 "#PWR032" H 2975 6825 50  0001 C CNN
F 1 "GND" H 2980 6902 50  0000 C CNN
F 2 "" H 2975 7075 50  0001 C CNN
F 3 "" H 2975 7075 50  0001 C CNN
	1    2975 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	2975 6375 2975 6575
Wire Wire Line
	2975 6875 2975 7075
$Comp
L power:VCC #PWR033
U 1 1 5B96A2E2
P 3225 6375
F 0 "#PWR033" H 3225 6225 50  0001 C CNN
F 1 "VCC" H 3242 6548 50  0000 C CNN
F 2 "" H 3225 6375 50  0001 C CNN
F 3 "" H 3225 6375 50  0001 C CNN
	1    3225 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C11
U 1 1 5B96A2E8
P 3225 6725
F 0 "C11" H 3245 6805 50  0000 L CNN
F 1 "0.1" H 3245 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 3263 6575 50  0001 C CNN
F 3 "~" H 3225 6725 50  0001 C CNN
	1    3225 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR034
U 1 1 5B96A2EF
P 3225 7075
F 0 "#PWR034" H 3225 6825 50  0001 C CNN
F 1 "GND" H 3230 6902 50  0000 C CNN
F 2 "" H 3225 7075 50  0001 C CNN
F 3 "" H 3225 7075 50  0001 C CNN
	1    3225 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	3225 6375 3225 6575
Wire Wire Line
	3225 6875 3225 7075
$Comp
L power:VCC #PWR035
U 1 1 5B96B15B
P 3475 6375
F 0 "#PWR035" H 3475 6225 50  0001 C CNN
F 1 "VCC" H 3492 6548 50  0000 C CNN
F 2 "" H 3475 6375 50  0001 C CNN
F 3 "" H 3475 6375 50  0001 C CNN
	1    3475 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C12
U 1 1 5B96B161
P 3475 6725
F 0 "C12" H 3495 6805 50  0000 L CNN
F 1 "0.1" H 3495 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 3513 6575 50  0001 C CNN
F 3 "~" H 3475 6725 50  0001 C CNN
	1    3475 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR036
U 1 1 5B96B168
P 3475 7075
F 0 "#PWR036" H 3475 6825 50  0001 C CNN
F 1 "GND" H 3480 6902 50  0000 C CNN
F 2 "" H 3475 7075 50  0001 C CNN
F 3 "" H 3475 7075 50  0001 C CNN
	1    3475 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	3475 6375 3475 6575
Wire Wire Line
	3475 6875 3475 7075
$Comp
L power:VCC #PWR037
U 1 1 5B96C10A
P 3725 6375
F 0 "#PWR037" H 3725 6225 50  0001 C CNN
F 1 "VCC" H 3742 6548 50  0000 C CNN
F 2 "" H 3725 6375 50  0001 C CNN
F 3 "" H 3725 6375 50  0001 C CNN
	1    3725 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C13
U 1 1 5B96C110
P 3725 6725
F 0 "C13" H 3745 6805 50  0000 L CNN
F 1 "0.1" H 3745 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 3763 6575 50  0001 C CNN
F 3 "~" H 3725 6725 50  0001 C CNN
	1    3725 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR038
U 1 1 5B96C117
P 3725 7075
F 0 "#PWR038" H 3725 6825 50  0001 C CNN
F 1 "GND" H 3730 6902 50  0000 C CNN
F 2 "" H 3725 7075 50  0001 C CNN
F 3 "" H 3725 7075 50  0001 C CNN
	1    3725 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	3725 6375 3725 6575
Wire Wire Line
	3725 6875 3725 7075
$Comp
L power:VCC #PWR039
U 1 1 5B96D207
P 3975 6375
F 0 "#PWR039" H 3975 6225 50  0001 C CNN
F 1 "VCC" H 3992 6548 50  0000 C CNN
F 2 "" H 3975 6375 50  0001 C CNN
F 3 "" H 3975 6375 50  0001 C CNN
	1    3975 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C14
U 1 1 5B96D20D
P 3975 6725
F 0 "C14" H 3995 6805 50  0000 L CNN
F 1 "0.1" H 3995 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 4013 6575 50  0001 C CNN
F 3 "~" H 3975 6725 50  0001 C CNN
	1    3975 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR040
U 1 1 5B96D214
P 3975 7075
F 0 "#PWR040" H 3975 6825 50  0001 C CNN
F 1 "GND" H 3980 6902 50  0000 C CNN
F 2 "" H 3975 7075 50  0001 C CNN
F 3 "" H 3975 7075 50  0001 C CNN
	1    3975 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	3975 6375 3975 6575
Wire Wire Line
	3975 6875 3975 7075
$Comp
L power:VCC #PWR041
U 1 1 5B96E448
P 4225 6375
F 0 "#PWR041" H 4225 6225 50  0001 C CNN
F 1 "VCC" H 4242 6548 50  0000 C CNN
F 2 "" H 4225 6375 50  0001 C CNN
F 3 "" H 4225 6375 50  0001 C CNN
	1    4225 6375
	1    0    0    -1  
$EndComp
$Comp
L Device:C C15
U 1 1 5B96E44E
P 4225 6725
F 0 "C15" H 4245 6805 50  0000 L CNN
F 1 "0.1" H 4245 6635 50  0000 L CNN
F 2 "Capacitors_SMD:C_1206_HandSoldering" H 4263 6575 50  0001 C CNN
F 3 "~" H 4225 6725 50  0001 C CNN
	1    4225 6725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR042
U 1 1 5B96E455
P 4225 7075
F 0 "#PWR042" H 4225 6825 50  0001 C CNN
F 1 "GND" H 4230 6902 50  0000 C CNN
F 2 "" H 4225 7075 50  0001 C CNN
F 3 "" H 4225 7075 50  0001 C CNN
	1    4225 7075
	1    0    0    -1  
$EndComp
Wire Wire Line
	4225 6375 4225 6575
Wire Wire Line
	4225 6875 4225 7075
$Comp
L SparkFun-LED:WS2812B D4
U 1 1 5B957FB4
P 7200 2100
F 0 "D4" H 7200 2660 45  0000 C CNN
F 1 "WS2812B" H 7200 2576 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 7200 2550 20  0001 C CNN
F 3 "" H 7200 2100 50  0001 C CNN
F 4 "DIO-12503" H 7200 2481 60  0000 C CNN "Field4"
	1    7200 2100
	1    0    0    -1  
$EndComp
Wire Wire Line
	1100 1325 3175 1325
Wire Wire Line
	3175 1325 3175 1900
$Comp
L power:VCC #PWR055
U 1 1 5B998A28
P 6475 1725
F 0 "#PWR055" H 6475 1575 50  0001 C CNN
F 1 "VCC" H 6492 1898 50  0000 C CNN
F 2 "" H 6475 1725 50  0001 C CNN
F 3 "" H 6475 1725 50  0001 C CNN
	1    6475 1725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR056
U 1 1 5B998A5F
P 6475 2325
F 0 "#PWR056" H 6475 2075 50  0001 C CNN
F 1 "GND" H 6480 2152 50  0000 C CNN
F 2 "" H 6475 2325 50  0001 C CNN
F 3 "" H 6475 2325 50  0001 C CNN
	1    6475 2325
	1    0    0    -1  
$EndComp
Wire Wire Line
	6475 1725 6475 1900
Wire Wire Line
	6475 1900 6650 1900
Wire Wire Line
	6475 2325 6475 2200
Wire Wire Line
	6475 2200 6650 2200
$Comp
L power:VCC #PWR061
U 1 1 5B9A026D
P 8050 1725
F 0 "#PWR061" H 8050 1575 50  0001 C CNN
F 1 "VCC" H 8067 1898 50  0000 C CNN
F 2 "" H 8050 1725 50  0001 C CNN
F 3 "" H 8050 1725 50  0001 C CNN
	1    8050 1725
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR062
U 1 1 5B9A0273
P 8050 2350
F 0 "#PWR062" H 8050 2100 50  0001 C CNN
F 1 "GND" H 8055 2177 50  0000 C CNN
F 2 "" H 8050 2350 50  0001 C CNN
F 3 "" H 8050 2350 50  0001 C CNN
	1    8050 2350
	1    0    0    -1  
$EndComp
Wire Wire Line
	8050 1725 8050 1900
Wire Wire Line
	8050 1900 8225 1900
Wire Wire Line
	8050 2350 8050 2200
Wire Wire Line
	8050 2200 8225 2200
Wire Wire Line
	6175 2200 6325 2200
Wire Wire Line
	6325 2200 6325 2625
Wire Wire Line
	6325 2625 7850 2625
Wire Wire Line
	7850 2625 7850 1900
Wire Wire Line
	7850 1900 7750 1900
Wire Wire Line
	7750 2200 7900 2200
Wire Wire Line
	7900 2200 7900 2625
Wire Wire Line
	7900 2625 9425 2625
Wire Wire Line
	9425 2625 9425 1900
Wire Wire Line
	9425 1900 9325 1900
$Comp
L SparkFun-LED:WS2812B D6
U 1 1 5B9B4255
P 2525 3525
F 0 "D6" H 2525 4085 45  0000 C CNN
F 1 "WS2812B" H 2525 4001 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 2525 3975 20  0001 C CNN
F 3 "" H 2525 3525 50  0001 C CNN
F 4 "DIO-12503" H 2525 3906 60  0000 C CNN "Field4"
	1    2525 3525
	1    0    0    -1  
$EndComp
$Comp
L SparkFun-LED:WS2812B D7
U 1 1 5B9B425D
P 4000 3525
F 0 "D7" H 4000 4085 45  0000 C CNN
F 1 "WS2812B" H 4000 4001 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 4000 3975 20  0001 C CNN
F 3 "" H 4000 3525 50  0001 C CNN
F 4 "DIO-12503" H 4000 3906 60  0000 C CNN "Field4"
	1    4000 3525
	1    0    0    -1  
$EndComp
$Comp
L SparkFun-LED:WS2812B D8
U 1 1 5B9B4265
P 5625 3525
F 0 "D8" H 5625 4085 45  0000 C CNN
F 1 "WS2812B" H 5625 4001 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 5625 3975 20  0001 C CNN
F 3 "" H 5625 3525 50  0001 C CNN
F 4 "DIO-12503" H 5625 3906 60  0000 C CNN "Field4"
	1    5625 3525
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR044
U 1 1 5B9B426C
P 1900 3725
F 0 "#PWR044" H 1900 3475 50  0001 C CNN
F 1 "GND" H 1905 3552 50  0000 C CNN
F 2 "" H 1900 3725 50  0001 C CNN
F 3 "" H 1900 3725 50  0001 C CNN
	1    1900 3725
	1    0    0    -1  
$EndComp
Wire Wire Line
	1900 3725 1900 3625
Wire Wire Line
	1900 3625 1975 3625
$Comp
L power:GND #PWR048
U 1 1 5B9B4274
P 3375 3750
F 0 "#PWR048" H 3375 3500 50  0001 C CNN
F 1 "GND" H 3380 3577 50  0000 C CNN
F 2 "" H 3375 3750 50  0001 C CNN
F 3 "" H 3375 3750 50  0001 C CNN
	1    3375 3750
	1    0    0    -1  
$EndComp
Wire Wire Line
	3375 3750 3375 3625
Wire Wire Line
	3375 3625 3450 3625
$Comp
L power:GND #PWR052
U 1 1 5B9B427C
P 4925 3750
F 0 "#PWR052" H 4925 3500 50  0001 C CNN
F 1 "GND" H 4930 3577 50  0000 C CNN
F 2 "" H 4925 3750 50  0001 C CNN
F 3 "" H 4925 3750 50  0001 C CNN
	1    4925 3750
	1    0    0    -1  
$EndComp
Wire Wire Line
	5075 3625 4925 3625
Wire Wire Line
	4925 3625 4925 3750
$Comp
L power:VCC #PWR043
U 1 1 5B9B4284
P 1900 3175
F 0 "#PWR043" H 1900 3025 50  0001 C CNN
F 1 "VCC" H 1917 3348 50  0000 C CNN
F 2 "" H 1900 3175 50  0001 C CNN
F 3 "" H 1900 3175 50  0001 C CNN
	1    1900 3175
	1    0    0    -1  
$EndComp
Wire Wire Line
	1900 3175 1900 3325
Wire Wire Line
	1900 3325 1975 3325
$Comp
L power:VCC #PWR047
U 1 1 5B9B428C
P 3375 3175
F 0 "#PWR047" H 3375 3025 50  0001 C CNN
F 1 "VCC" H 3392 3348 50  0000 C CNN
F 2 "" H 3375 3175 50  0001 C CNN
F 3 "" H 3375 3175 50  0001 C CNN
	1    3375 3175
	1    0    0    -1  
$EndComp
Wire Wire Line
	3375 3175 3375 3325
Wire Wire Line
	3375 3325 3450 3325
$Comp
L power:VCC #PWR051
U 1 1 5B9B4294
P 4925 3150
F 0 "#PWR051" H 4925 3000 50  0001 C CNN
F 1 "VCC" H 4942 3323 50  0000 C CNN
F 2 "" H 4925 3150 50  0001 C CNN
F 3 "" H 4925 3150 50  0001 C CNN
	1    4925 3150
	1    0    0    -1  
$EndComp
Wire Wire Line
	4925 3150 4925 3325
Wire Wire Line
	4925 3325 5075 3325
Wire Wire Line
	3075 3625 3175 3625
Wire Wire Line
	3175 3625 3175 4050
Wire Wire Line
	3175 4050 4600 4050
Wire Wire Line
	4600 4050 4600 3325
Wire Wire Line
	4600 3325 4550 3325
Wire Wire Line
	4550 3625 4675 3625
Wire Wire Line
	4675 3625 4675 4050
Wire Wire Line
	4675 4050 6250 4050
Wire Wire Line
	6250 4050 6250 3325
Wire Wire Line
	6250 3325 6175 3325
$Comp
L SparkFun-LED:WS2812B D10
U 1 1 5B9B42A8
P 8775 3525
F 0 "D10" H 8775 4085 45  0000 C CNN
F 1 "WS2812B" H 8775 4001 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 8775 3975 20  0001 C CNN
F 3 "" H 8775 3525 50  0001 C CNN
F 4 "DIO-12503" H 8775 3906 60  0000 C CNN "Field4"
	1    8775 3525
	1    0    0    -1  
$EndComp
$Comp
L SparkFun-LED:WS2812B D9
U 1 1 5B9B42B0
P 7200 3525
F 0 "D9" H 7200 4085 45  0000 C CNN
F 1 "WS2812B" H 7200 4001 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 7200 3975 20  0001 C CNN
F 3 "" H 7200 3525 50  0001 C CNN
F 4 "DIO-12503" H 7200 3906 60  0000 C CNN "Field4"
	1    7200 3525
	1    0    0    -1  
$EndComp
$Comp
L power:VCC #PWR057
U 1 1 5B9B42B8
P 6475 3150
F 0 "#PWR057" H 6475 3000 50  0001 C CNN
F 1 "VCC" H 6492 3323 50  0000 C CNN
F 2 "" H 6475 3150 50  0001 C CNN
F 3 "" H 6475 3150 50  0001 C CNN
	1    6475 3150
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR058
U 1 1 5B9B42BE
P 6475 3750
F 0 "#PWR058" H 6475 3500 50  0001 C CNN
F 1 "GND" H 6480 3577 50  0000 C CNN
F 2 "" H 6475 3750 50  0001 C CNN
F 3 "" H 6475 3750 50  0001 C CNN
	1    6475 3750
	1    0    0    -1  
$EndComp
Wire Wire Line
	6475 3150 6475 3325
Wire Wire Line
	6475 3325 6650 3325
Wire Wire Line
	6475 3750 6475 3625
Wire Wire Line
	6475 3625 6650 3625
$Comp
L power:VCC #PWR063
U 1 1 5B9B42C8
P 8050 3150
F 0 "#PWR063" H 8050 3000 50  0001 C CNN
F 1 "VCC" H 8067 3323 50  0000 C CNN
F 2 "" H 8050 3150 50  0001 C CNN
F 3 "" H 8050 3150 50  0001 C CNN
	1    8050 3150
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR064
U 1 1 5B9B42CE
P 8050 3775
F 0 "#PWR064" H 8050 3525 50  0001 C CNN
F 1 "GND" H 8055 3602 50  0000 C CNN
F 2 "" H 8050 3775 50  0001 C CNN
F 3 "" H 8050 3775 50  0001 C CNN
	1    8050 3775
	1    0    0    -1  
$EndComp
Wire Wire Line
	8050 3150 8050 3325
Wire Wire Line
	8050 3325 8225 3325
Wire Wire Line
	8050 3775 8050 3625
Wire Wire Line
	8050 3625 8225 3625
Wire Wire Line
	6175 3625 6325 3625
Wire Wire Line
	6325 3625 6325 4050
Wire Wire Line
	6325 4050 7850 4050
Wire Wire Line
	7850 4050 7850 3325
Wire Wire Line
	7850 3325 7750 3325
Wire Wire Line
	7750 3625 7900 3625
Wire Wire Line
	7900 3625 7900 4050
Wire Wire Line
	7900 4050 9425 4050
Wire Wire Line
	9425 4050 9425 3325
Wire Wire Line
	9425 3325 9325 3325
$Comp
L SparkFun-LED:WS2812B D11
U 1 1 5B9B7E8F
P 2525 5000
F 0 "D11" H 2525 5560 45  0000 C CNN
F 1 "WS2812B" H 2525 5476 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 2525 5450 20  0001 C CNN
F 3 "" H 2525 5000 50  0001 C CNN
F 4 "DIO-12503" H 2525 5381 60  0000 C CNN "Field4"
	1    2525 5000
	1    0    0    -1  
$EndComp
$Comp
L SparkFun-LED:WS2812B D12
U 1 1 5B9B7E97
P 4000 5000
F 0 "D12" H 4000 5560 45  0000 C CNN
F 1 "WS2812B" H 4000 5476 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 4000 5450 20  0001 C CNN
F 3 "" H 4000 5000 50  0001 C CNN
F 4 "DIO-12503" H 4000 5381 60  0000 C CNN "Field4"
	1    4000 5000
	1    0    0    -1  
$EndComp
$Comp
L SparkFun-LED:WS2812B D13
U 1 1 5B9B7E9F
P 5625 5000
F 0 "D13" H 5625 5560 45  0000 C CNN
F 1 "WS2812B" H 5625 5476 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 5625 5450 20  0001 C CNN
F 3 "" H 5625 5000 50  0001 C CNN
F 4 "DIO-12503" H 5625 5381 60  0000 C CNN "Field4"
	1    5625 5000
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR046
U 1 1 5B9B7EA6
P 1900 5200
F 0 "#PWR046" H 1900 4950 50  0001 C CNN
F 1 "GND" H 1905 5027 50  0000 C CNN
F 2 "" H 1900 5200 50  0001 C CNN
F 3 "" H 1900 5200 50  0001 C CNN
	1    1900 5200
	1    0    0    -1  
$EndComp
Wire Wire Line
	1900 5200 1900 5100
Wire Wire Line
	1900 5100 1975 5100
$Comp
L power:GND #PWR050
U 1 1 5B9B7EAE
P 3375 5225
F 0 "#PWR050" H 3375 4975 50  0001 C CNN
F 1 "GND" H 3380 5052 50  0000 C CNN
F 2 "" H 3375 5225 50  0001 C CNN
F 3 "" H 3375 5225 50  0001 C CNN
	1    3375 5225
	1    0    0    -1  
$EndComp
Wire Wire Line
	3375 5225 3375 5100
Wire Wire Line
	3375 5100 3450 5100
$Comp
L power:GND #PWR054
U 1 1 5B9B7EB6
P 4925 5225
F 0 "#PWR054" H 4925 4975 50  0001 C CNN
F 1 "GND" H 4930 5052 50  0000 C CNN
F 2 "" H 4925 5225 50  0001 C CNN
F 3 "" H 4925 5225 50  0001 C CNN
	1    4925 5225
	1    0    0    -1  
$EndComp
Wire Wire Line
	5075 5100 4925 5100
Wire Wire Line
	4925 5100 4925 5225
$Comp
L power:VCC #PWR045
U 1 1 5B9B7EBE
P 1900 4650
F 0 "#PWR045" H 1900 4500 50  0001 C CNN
F 1 "VCC" H 1917 4823 50  0000 C CNN
F 2 "" H 1900 4650 50  0001 C CNN
F 3 "" H 1900 4650 50  0001 C CNN
	1    1900 4650
	1    0    0    -1  
$EndComp
Wire Wire Line
	1900 4650 1900 4800
Wire Wire Line
	1900 4800 1975 4800
$Comp
L power:VCC #PWR049
U 1 1 5B9B7EC6
P 3375 4650
F 0 "#PWR049" H 3375 4500 50  0001 C CNN
F 1 "VCC" H 3392 4823 50  0000 C CNN
F 2 "" H 3375 4650 50  0001 C CNN
F 3 "" H 3375 4650 50  0001 C CNN
	1    3375 4650
	1    0    0    -1  
$EndComp
Wire Wire Line
	3375 4650 3375 4800
Wire Wire Line
	3375 4800 3450 4800
$Comp
L power:VCC #PWR053
U 1 1 5B9B7ECE
P 4925 4625
F 0 "#PWR053" H 4925 4475 50  0001 C CNN
F 1 "VCC" H 4942 4798 50  0000 C CNN
F 2 "" H 4925 4625 50  0001 C CNN
F 3 "" H 4925 4625 50  0001 C CNN
	1    4925 4625
	1    0    0    -1  
$EndComp
Wire Wire Line
	4925 4625 4925 4800
Wire Wire Line
	4925 4800 5075 4800
Wire Wire Line
	3075 5100 3175 5100
Wire Wire Line
	3175 5100 3175 5525
Wire Wire Line
	3175 5525 4600 5525
Wire Wire Line
	4600 5525 4600 4800
Wire Wire Line
	4600 4800 4550 4800
Wire Wire Line
	4550 5100 4675 5100
Wire Wire Line
	4675 5100 4675 5525
Wire Wire Line
	4675 5525 6250 5525
Wire Wire Line
	6250 5525 6250 4800
Wire Wire Line
	6250 4800 6175 4800
$Comp
L SparkFun-LED:WS2812B D15
U 1 1 5B9B7EE2
P 8775 5000
F 0 "D15" H 8775 5560 45  0000 C CNN
F 1 "WS2812B" H 8775 5476 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 8775 5450 20  0001 C CNN
F 3 "" H 8775 5000 50  0001 C CNN
F 4 "DIO-12503" H 8775 5381 60  0000 C CNN "Field4"
	1    8775 5000
	1    0    0    -1  
$EndComp
$Comp
L SparkFun-LED:WS2812B D14
U 1 1 5B9B7EEA
P 7200 5000
F 0 "D14" H 7200 5560 45  0000 C CNN
F 1 "WS2812B" H 7200 5476 45  0000 C CNN
F 2 "Sparkfun LEDs:WS2812B" H 7200 5450 20  0001 C CNN
F 3 "" H 7200 5000 50  0001 C CNN
F 4 "DIO-12503" H 7200 5381 60  0000 C CNN "Field4"
	1    7200 5000
	1    0    0    -1  
$EndComp
$Comp
L power:VCC #PWR059
U 1 1 5B9B7EF2
P 6475 4625
F 0 "#PWR059" H 6475 4475 50  0001 C CNN
F 1 "VCC" H 6492 4798 50  0000 C CNN
F 2 "" H 6475 4625 50  0001 C CNN
F 3 "" H 6475 4625 50  0001 C CNN
	1    6475 4625
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR060
U 1 1 5B9B7EF8
P 6475 5225
F 0 "#PWR060" H 6475 4975 50  0001 C CNN
F 1 "GND" H 6480 5052 50  0000 C CNN
F 2 "" H 6475 5225 50  0001 C CNN
F 3 "" H 6475 5225 50  0001 C CNN
	1    6475 5225
	1    0    0    -1  
$EndComp
Wire Wire Line
	6475 4625 6475 4800
Wire Wire Line
	6475 4800 6650 4800
Wire Wire Line
	6475 5225 6475 5100
Wire Wire Line
	6475 5100 6650 5100
$Comp
L power:VCC #PWR065
U 1 1 5B9B7F02
P 8050 4625
F 0 "#PWR065" H 8050 4475 50  0001 C CNN
F 1 "VCC" H 8067 4798 50  0000 C CNN
F 2 "" H 8050 4625 50  0001 C CNN
F 3 "" H 8050 4625 50  0001 C CNN
	1    8050 4625
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR066
U 1 1 5B9B7F08
P 8050 5250
F 0 "#PWR066" H 8050 5000 50  0001 C CNN
F 1 "GND" H 8055 5077 50  0000 C CNN
F 2 "" H 8050 5250 50  0001 C CNN
F 3 "" H 8050 5250 50  0001 C CNN
	1    8050 5250
	1    0    0    -1  
$EndComp
Wire Wire Line
	8050 4625 8050 4800
Wire Wire Line
	8050 4800 8225 4800
Wire Wire Line
	8050 5250 8050 5100
Wire Wire Line
	8050 5100 8225 5100
Wire Wire Line
	6175 5100 6325 5100
Wire Wire Line
	6325 5100 6325 5525
Wire Wire Line
	6325 5525 7850 5525
Wire Wire Line
	7850 5525 7850 4800
Wire Wire Line
	7850 4800 7750 4800
Wire Wire Line
	7750 5100 7900 5100
Wire Wire Line
	7900 5100 7900 5525
Wire Wire Line
	7900 5525 9425 5525
Wire Wire Line
	9425 5525 9425 4800
Wire Wire Line
	9425 4800 9325 4800
Wire Wire Line
	9325 2200 9500 2200
Wire Wire Line
	9500 2200 9500 2750
Wire Wire Line
	9500 2750 3175 2750
Wire Wire Line
	3175 2750 3175 3325
Wire Wire Line
	3175 3325 3075 3325
Wire Wire Line
	9325 3625 9500 3625
Wire Wire Line
	9500 3625 9500 4200
Wire Wire Line
	9500 4200 3175 4200
Wire Wire Line
	3175 4200 3175 4800
Wire Wire Line
	3175 4800 3075 4800
Wire Wire Line
	9325 5100 9625 5100
Wire Wire Line
	9625 5100 9625 1225
Wire Wire Line
	9625 1225 9950 1225
Wire Wire Line
	9950 1325 9800 1325
Wire Wire Line
	9800 1325 9800 1400
$EndSCHEMATC