.ORIG x3000
START LEA R0, startmsg  ; load the address of string and store in R0
      PUTS
      LEA R0, msg
      PUTS
      GETC            ; or GETC and/or a PUTC, which allow us to echo to console (get input from user and store in R0)
      LD R2, E 
      ADD R2, R2, R0    ; input minus E
      BRz encrypt1
    
      LD R2 D 
      ADD R2, R2, R0  ; input minus D
      BRz decrypt1
      
      LD R2 X    ; input minus x
      Add R2, R2, R0
      BRz clear1
      
      BRnzp else
      
      encrypt1 JSR subEncrypt
      BRnzp Start
      decrypt1 JSR subDecrypt
      BRnzp Start
      clear1 JSR subClear 
      BRnzp Start
msg .STRINGZ "\nENTER E OR D OR X\n"
msg2 .STRINGZ "\nENTER KEY \n"
invalid .STRINGZ "\nINVALID INPUT\n"
msg3 .STRINGZ "\nENTER MESSAGE\n"
startmsg    .STRINGZ "\nSTARTING PRIVACY MODULE\n"
STORE_x5000 .FILL x5000
STORE_x4200 .FILL x4200
STORE_R7_Encrypt .FILL x4300
STORE_R7_caesar .FILL x4301
STORE_R7_Decrypt .FILL x4302
STORE_R7_clear .FILL x4303
STORE_R7_getKey .FILL x4304
STORE_R7_getMsg .FILL x4305
STORE_R7_vignenere .FILL x4306
STORE_R7_UNcaesar .FILL x4307
keyCheck .FILL x4400
msgCheck .FILL x4401
Message .FILL x4000
Decrypt .FILL x5000


tfs .FILL #257
wts .FILL #-127
fs .FILL #-57
fe .FILL #-48
E .FILL x-45 ; ASCII E
D .FILL x-44 ; ASCII D
X .FILL x-58 ; ASCII X
pc .FILL #-32
keyFail .FILL x4401
god .FILL x4500

STORE_x4100 .FILL x4100
god2 .FILL x4501
god3 .FILL x4502
god4 .FILL x4503
god5 .FILL x4504
d100 .FILL x4505



STORE_R7_unVignenere .FILL x4308
subEncrypt   ; subroutine to 
    STI R7 STORE_R7_Encrypt
    JSR getKey
    JSR getMsg
    JSR vignenere
    JSR caesar
    LDI R7 STORE_R7_Encrypt
    Ret 
    
subDecrypt  ; subroutine to 
    STI R7 STORE_R7_Decrypt
    JSR unCaesar
    JSR unVignenere
    LDI R7 STORE_R7_Decrypt
    ret 

subClear   ; subroutine to 
    STI R7 STORE_R7_clear
    LD R5 Message
    AND R4 R4 #0
    ADD R4 R4 #10
loop7
    AND R3 R3 #0
    STR R3 R5 #0
    ADD R5 R5 #1
    ADD R4 R4 #-1
    Brp loop7
    HALT
    LDI R7 STORE_R7_clear
    ret

else 
    LEA R0, invalid
    PUTS 
    brnzp START
    ret
    
    
getKey
        STI R7 STORE_R7_getKey
        AND R2 R2 #0  ; R2=0
        ADD R2 R2 #5  ; R2 = 5 for counter
        LD R3 STORE_x4100  ; R3 = key location
        LEA R0, msg2     ; R0 holds msg2
        PUTS            ; print(msg2)
        
loop    
      
        GETC              ; R0 holds key
        AND R4 R4 #0    ; R4=0
        STR R0 R3 #0    ; Store R0 key location
        
        ADD R3 R3 #1    ; move to next key location
        ADD R2 R2 #-1   ; decrement counter
        
        BRp loop
        
        LD R3 STORE_x4100
        LDR R4 R3 #2    ; R1 holds ceaserkey digit 1
        LDR R2 R3 #3    ; R2 holds ceasekey digit 2
        LDR R1 R3 #4    ; R3 holds ceaserkey digit 3
        LDR R6 R3 #0    ; R3 holds ceaserkey digit 3
        
        LDR R5 R3 #1    ; R3 holds ceaserkey digit 3
       
        LD R0 fe 
        ADD R4 R4 R0
        ADD R2 R2 R0
        ADD R1 R1 R0
        ADD R6 R6 R0
        ADD R5 R5 R0
        BRnp notzero
        STR R5 R3 #1
notzero 
        STR R4 R3 #2    ;store ascii to decimal conversion
        STR R2 R3 #3    ;store ascii to decimal conversion
        STR R1 R3 #4    ;store ascii to decimal conversion
        STR R6 R3 #0    ;store ascii to decimal conversion
       
        
        LDR R0 R3 #2    ; R0=digit1
        
        LD R1 dig3     ; R1=100
        JSR subMult     ; (digit1 *100)
        
        
        AND R4 R4 #0    
        ADD R4 R6 R4    ; R4 =(digit1 *100)
        STI R4 d100
        AND R0 R0 #0    ; clear R0
        AND R1 R1 #0    ; clear R1
        LDR R0 R3 #3    ; R0 = digit2
        ADD R1 R1 #10   ; R1 = 10
        JSR subMult     ; (10*digit2)
        LDI R4 d100     ; R4 =(digit1 *100)
        LDR R1 R3 #4    ; R1 = digit3
        ADD R2 R6 R1    ; (10*digit2) + digit3
        ADD R5 R4 R2    ; ((10*digit2) + digit3)+(digit1 *100)) holds full key
        LD R3 STORE_x4100 
        STR R5 R3 #5
        JSR checkKey    ;checks key, R6 = 0 if invalid, R6 = 1 if valid
        LDI R6 keyCheck ;  
        BRp done        ;skips invalid message if R6 = 1
        LEA R0 invalid
        PUTS            ;prints invalid message
        JSR getKey      ;try again
    done
        
        LDI R7 STORE_R7_getKey
        Ret
checkKey
        LD R5 STORE_x4100
        AND R6 R6 #0
        STI R6 keyCheck     ;set return value to 0
        LDR R0 R5 #0        ;load first key
        Brn fin            ;failed 
        Brz key2            ;key passes, onto key 2
        AND R2 R2 #0
        ADD R2 R2 #-8       ;R2=-8
        ADD R0 R2 R0        ;R0=R0-8
        BRp fin             ;if R0 is positive, key is invalid
 key2
        LDR R0 R5 #1         ; R0=key2 
        BRz key3            ; if key2 is 0, key2 is valid
        BRn fin             ; if key2 is negative, key is invalid
        LD R2 fs            ;R2=-57
        ADD R3 R0 R2        ;R3=key2 -57
        BRp key3            ; if key2 is bigger than -57, key2 is valid
        BRz fin             ; if key2=57, key is invalid
        LD R2 fe            ;R2=-48
        ADD R3 R0 R2        ;R3=key-48
        BRp fin             ;if key is bigger than 48 and less than 57, key 2 fails
 key3
        LD R5 STORE_x4100   
        LDR R0 R5 #5        ;R0 holds caeser key in decimal
        BRn fin             ; if it is negative, the key is invalid
        BRz done1           ; if it is zero, the key is valid
        LD R1 wts           ; R1=-127
        ADD R0 R0 R1       ; R0=R0-127
        BRp fin             ; if R0 > 127, the key is invalid
        
        
done1   ADD R6 R6 #1        ; the key is valid, return 1
        STI R6 keyCheck     ; change the keycheck value to 1

    fin   Ret
getMsg
        STI R7 STORE_R7_getMsg
        AND R2 R2 #0    ;R2=0
        ADD R2 R2 #10   ;R2=10
        LD R3 Message ;R3= start of message
        LEA R0, msg3 ;R0 =msg3
        PUTS        ;printf(msg3)
        
loop2
        getc          ;R0 = newChar
        STR R0 R3 #0 ; Store newChar into message location
        AND R4 R4 #0 ; R4 = 0
        ADD R3 R3 #1 ; move to next char Location
        
        ADD R2 R2 #-1 ; decrement counter
        BRp loop2
        
        JSR checkMsg    ;checks key, R6 = 0 if invalid, R6 = 1 if valid
        LDI R6 msgCheck ;  
        BRp done2        ;skips invalid message if R6 = 1
        LEA R0 invalid
        PUTS            ;prints invalid message
        JSR getMsg      ;try again
    done2
        LDI R7 STORE_R7_getMsg
    Ret
    
checkMsg
    LD R5 Message
    AND R4 R4 #0
    ADD R4 R4 #10
        
loop9
    LDR R0 R5 #0            ;R0=current char
    AND R6 R6 #0            ;R6=0
    STI R6 msgCheck         ;msgCheck=0
    LD R1 pc                ; R1=-32
    ADD R2 R0 R1            ;R2= (current char-32)
    BRn fin2                ;msg invalid if R2 is positive
    ADD R5 R5 #1            ;increment to next char location
    LD R1 wts               ;R1=-127
    ADD R2 R1 R0            ;R2=(current char -127)
    Brp fin2
    ADD R4 R4 #-1           ;Decrement counter
    BRp loop9
    ADD R6 R6 #1
    STI R6 msgCheck
    
fin2   
    ret  

caesar 
        
        AND R5 R5 #0
        AND R6 R6 #0
        AND R0 R0 #0
        AND R1 R1 #0
        STI R7 STORE_R7_caesar
         
        LD R5 Message   ; R5 holds Start of message
        AND R4 R4 #0        ; R4=0
        ADD R4 R4 #10       ; R4=10
        AND R0 R0 #0        ; R0=0
        AND R1 R1 #0        ; R1=0
        LD R3 STORE_x4100
        LDR R2 R3 #5    ;R2=full key
        LD R5 Message
loop4
        LDR R0 R5 #0    ; R0=current char
        ADD R0 R0 R2    ; R0=(Full Key+current char)
        LD R1 N         ; R1 =128
        STI R2 god4
        JSR MODK       ; R6=((Full Key+char1)%128)
        LDI R2 god4
        STR R6 R5 #0    ; Store encrypted char
        ADD R5 R5 #1    ; Move to next char
        ADD R4 R4 #-1   ; Decrement the counter
        Brp loop4
        
        LDI R7 STORE_R7_caesar
    Ret
unCaesar
    AND R5 R5 #0
        AND R6 R6 #0
        AND R0 R0 #0
        AND R1 R1 #0
        STI R7 STORE_R7_caesar
         
        LD R5 Message       ; R5 holds Start of message
        AND R4 R4 #0        ; R4=0
        ADD R4 R4 #10       ; R4=10
        AND R0 R0 #0        ; R0=0
        AND R1 R1 #0        ; R1=0
        LD R3 STORE_x4100
        LDR R2 R3 #5    ;R2=full key
        NOT R2 R2 
        ADD R2 R2 #1
        LD R5 Message
loop6
        LDR R0 R5 #0    ; R0=current char
        LD R3 N
        ADD R2 R2 R3    ;R2=(128-key)
        ADD R2 R2 R3
        ADD R0 R0 R2    ; R0=((128-Key)+current char)
        LD R1 N         ; R1 =128
        STI R2 god4
        JSR MODK       ; R6=((Full Key+char1)%128)
        LDI R2 god4
        STR R6 R5 #0    ; Store encrypted char
        ADD R5 R5 #1    ; Move to next char
        ADD R4 R4 #-1   ; Decrement the counter
        Brp loop6
        
        LDI R7 STORE_R7_caesar
    Ret
    
vignenere 
        STI R7 STORE_R7_vignenere
        AND R5 R5 #0
        LD R5 STORE_x4100 
        LDR R1 R5 #1     ; R1 holds key
        AND R5 R5 #0
        LD R5 Message  ; R5 = Start of message
        AND R4 R4 #0        ; R4=0
        ADD R4 R4 #10       ; R4=10 for the counter
        AND R0 R0 #0        ; R0=0
    loop3
        LDR R0 R5 #0    ; R0=current char
        STI R5 god
        STI R4 god2
        JSR XOR         ; R6=(char1 XOR key)
        LDI R4 god2
        LDI R5 god
        STR R6 R5 #0    ; Store encrypted char
        ADD R5 R5 #1   ;increment to next char location
        ADD R4 R4 #-1   ; Decrement the counter
        Brp loop3
        LDI R7 STORE_R7_vignenere
        Ret
unVignenere 
        STI R7 STORE_R7_unVignenere
        AND R5 R5 #0
        LD R5 STORE_x4100 
        LDR R1 R5 #1     ; R1 holds key
        AND R5 R5 #0
        LD R5 Message  ; R5 = Start of message
        AND R4 R4 #0        ; R4=0
        ADD R4 R4 #10       ; R4=10 for the counter
        AND R0 R0 #0        ; R0=0
        AND R3 R3 #0
        LD R3 Decrypt
    loop8
        LDR R0 R5 #0    ; R0=current char
        STI R5 god
        STI R4 god2
        STI R3 god5
        JSR XOR         ; R6=(current char XOR key)
        LDI R4 god2
        LDI R5 god
        LDI R3 god5
        STR R6 R3 #0    ; Store encrypted char
        ADD R3 R3 #1
        ADD R5 R5 #1   ;increment to next char location
        ADD R4 R4 #-1   ; Decrement the counter
        Brp loop8
        LDI R7 STORE_R7_unVignenere
        Ret


dig3 .FILL 100
N .FILL #128 
check5 .FILL #10000
XOR
    
    AND R3 R3 #0
    AND R4 R4 #0
    AND R5 R5 #0
    AND R6 R6 #0
    NOT R4 R0 ; R4=A'
    NOT R3 R1 ; R3=B'
    
    AND R5 R4 R1 ; R5= A' AND B 
    AND R6 R3 R0 ; R6= A AND B' 
    
    NOT R5 R5 ; R5=(A' AND B)' 
    NOT R6 R6 ; R6=(A AND B')' 
    
    AND R2 R6 R5 ; R2=((A' AND B)' AND (A AND B')')
    NOT R2 R2 ; R2=((A' AND B)' AND (A AND B')')
    AND R6 R6 #0 ; R6 =0
    ADD R6 R6 R2 ; R6 = A XOR B
    Ret 
MODK    
        AND R2 R2 #0 ; R2=0
        AND R3 R3 #0 ; R3=0
        ADD R2 R0 #0 ; R2=R0
        AND R6 R6 #0
        NOT R3 R1 ;    
        ADD R3 R3 #1 ; R3=-R1
    loop10
        ADD R2 R2 R3 ; R2=R2-R1
        BRp loop10 ; loop if theres more to be subtracted
        BRz noRem ; 0 remainder, return 0
        ADD R2, R2 R1 ; return to previous state
    
        ADD R6 R2 R6  ; set value to r6
   noRem Ret 
 
subSub ; subroutine to 
        NOT R1 R1
        ADD R1 R1 #1
        ADD R2 R0 R1
        AND R6 R6 #0
        ADD R6 R6 R2
        Ret 
subMult ; subroutine to 
  
        AND R4 R4 #0
        ADD R2 R1 #0
        AND R6 R6 #0
        ADD R6 R6 R2
loop5 
    ADD R4 R4 R0
    ADD R2 R2 #-1
    BRp loop5
    AND R6 R6 #0
    ADD R6 R6 R4
    Ret


.END
