# CircMan

This is a  two person, one on one battle game. The players can jump, kick, pick up objects, shoot from objects
and through objects.

Download the JAR to play.

To player two player use a second keyboard.

Note: Wiring on keyboards can limit the use of multiple key presses being registered. If you notice that multiple
key presses are not being registered this may be why. Many gaming keyboards are wired so every key press
will be registered independently of the other keys, but the standard keyboard on my Mac is not.

Note: Some computers will change the key representation if you hold the key down for a couple seconds.
Some Mac computers will have this setting on.

For Mac you can enter in the terminal

defaults write -g ApplePressAndHoldEnabled -bool false

Controls:
Player 1 keys:
i  :   up
K  :   down
J   :   left
L  :   right

A :  punch, pick up object, shoot
S : spin punch, through object

Player 2 keys:

R  :   up
F  :   down
D   :   left
G  :   right

Q :  punch, pick up object, shoot
W : spin punch, through object


Combos
Down+Punch   :    kick
Down+Punch+(while in the air)   :  downward spin kick
