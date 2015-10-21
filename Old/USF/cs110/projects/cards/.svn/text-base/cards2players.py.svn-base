#Pat McNamara

#Functions

#asks user for card value
def card(a, b):  #a is rank or suit, b is card number
    card = raw_input("Enter " + a + " of card " + str(b) + ": ").upper()  #takes input and makes it uppercase
    card = checkCard(card, a)  #makes sure the rank or suit is valid, if it isn't, new value will be stored in card
    return card  #returns card value

#checks if it is a valid rank or suit
def checkCard(a, b):  #a is card value, b tells if it is a rank or suit
    if b == "rank":  #tests rank
        x = 2  #checking to see if rank of card is a number
        while x <= 10:  #Checks numbers 2-10
            if str(x) == a:  #the test card must be a string because user generated card is also a string
                return a  #if the card is a valid number, it returns the card to the card function
            else:
                x += 1
        if a != "J" and a != "Q" and a != "K" and a != "A":  #check to see if card is a face card 
            a = raw_input("That is not a valid rank, please re-enter: ").upper()  #it it isn't a face card, asks for a differant number
            a = checkCard(a, b)
    else:  #will always be suit
        if a != "HEARTS" and a != "SPADES" and a != "DIAMONDS" and a!= "CLUBS":  #check for valid suit
            a = raw_input("This is not a valid suit, please re-enter: ").upper()  #asks for a differant suit
            a = checkCard(a, b)  #checks new number
    return a  #return correct value

#change face cards to numbers: input face card, output numerical value of card, no side-effects
def faceToNumber(card):
    if card == "J":
        return 11
    elif card == "Q":
        return 12
    elif card == "K":
        return 13
    elif card == "A":
        return 14
    else:
	return card

#change number to face card: input card number, output face card, no side effects
def numberToFace(card):
    if card == 11:
        return "Jack"
    if card == 12:
        return "Queen"
    if card == 13:
        return "King"
    if card == 14:
        return "Ace"

#take cards and sort them from highest to lowest: input rank of first 3 cards, output 3 cards in order from highest to lowest, no side-effects
def sort(card1, card2, card3):
    if card1 >= card2:
        switch = card1 #switch is local variable used as holding space
        card1 = card2
        card2 = switch
        
    if card2 >= card3:
        switch = card2
        card2 = card3
        card3 = switch
        
    if card1 >= card2: #must recheck incase lowest card was entered last
        switch = card1
        card1 = card2
        card2 = switch

    return card1, card2, card3

#test to see if there is a pair: input 3 ranks, output 1 if pairs = 0 if not equal, no side-effects
def testPair(rank1, rank2, rank3):
    if rank1 == rank2:  #compaires the 2 low cards
        pair1 = 1       #pair1 compairs low cards
    else: pair1 = 0
    if rank2 == rank3:  #compairs high cards
        pair2 = 1       #pair2 compairs high cards
    else: pair2=0
    return pair1, pair2

#test to see if it is a straight: input 3 ranks in order from highest to lowest, output 1 if it is a straight and 0 if it isn't, no side effects
def testStraight(rank1, rank2, rank3):
    if rank1 + 1 == rank2 and rank2 + 1 == rank3:  #tests if second card is 1 bigger then second and second is one bigger then their
        return 1
    else: return 0

#test for flush: input 3 suits, outputs 1 if hand is flush, 0 if it isn't, no side-effect
def testFlush(suit1, suit2, suit3):
    if suit1 == suit2 == suit3:
        return 1
    else: return 0

#If players have same combination, figures out winner: input rank of player 1 and 2, no output, calculates who had the better hand and prints winner
def tie(aRank, bRank):
    if aRank > bRank:
        print "Player 1 wins"
    elif aRank < bRank:
        print "Player 2 wins"
    else:
        print "The game is a draw"

#get cards ranks and returns highest combination, no input, output rank and suit of best good card and hand as a number, asks user for cards
def getHand():
    #runs variable functions
    rank1 = card("rank", 1)
    suit1 = card("suit", 1)
    rank2 = card("rank", 2)
    suit2 = card("suit", 2)
    rank3 = card("rank", 3)
    suit3 = card("suit", 3)
    
    #convert non-number cards and change all ranks to intergers
    rank1 = int(faceToNumber(rank1))
    rank2 = int(faceToNumber(rank2))
    rank3 = int(faceToNumber(rank3))

    #sort cards and test for pairs, straight or flush
    (rank1, rank2, rank3) = sort(rank1, rank2, rank3)  #Sort cards from lowest to highest
    (pair1, pair2) = testPair(rank1, rank2, rank3)  #Test for pair, if rank 1 = 2 pair1 changes, if rank 2=3, pair 2 changes
    if pair1 + pair2 == 0:  #if there is a pair, there can not be a straight or a flush
        straight = testStraight(rank1, rank2, rank3)  #Test for straight, if one exists, straight changes to 1
        flush = testFlush(suit1, suit2, suit3)  #Test for flush, if one exists, flush becomes 1
    else:
        straight = 0
        flush = 0

    #Test for each type of hand in order of priority -- where suit is not needed, suit 3 is returned just to match 3 returns needed for the straight flush
    if flush == 1 and straight
 == 1:   #straight flush
        return sank3, suit3, 1
    elif pair1 + pair2 == 2:           #3 of a kind
        return rank3, suit3, 2
    elif straight == 1:                #straight
        return rank3, suit3, 3
    elif flush == 1:                   #flush
        return rank3, suit3, 4
    elif pair1 + pair2 == 1:           #pair, returns card 2 because it is the only card used in both pair posibilites
        return rank2, suit3, 5
    else:                              #high card
        if rank3 > 10:                 #face cards need to be turned back from intergers
            rank3 = numberToFace(rank3)
        return rank3, suit3, 6

#End of functions

print "First person:"
aRank, aSuit, aHand = getHand() #a before name means player 1, hand ranked 1-6 with 1 being best
print "\nSecond person:"
bRank, bSuit, bHand = getHand() #b is player 2

#Who won - lower hand value wins, should be combined with the display  This should be a display sub fucntion
if aHand < bHand:  #player 1 wins
    print "Player 1 wins"  #Player 1's card beats player 2's card
elif aHand > bHand: #player 2 wins
    print "Player 2 wins"
else: #tie
    tie(aRank, bRank)
