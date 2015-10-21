#Pat McNamara

#Functions

#asks user for card value: input suit or rank wanted and the number of the card, output card value, no side-effects
def card(a, b):
    card = raw_input("Enter " + a + " of card " + str(b) + ": ").upper()  #takes input and makes it uppercase
    card = checkCard(card, a)  #makes sure the rank or suit is valid, if it isn't, new value will be stored in card
    return card

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
            a = checkCard(a, b)  #checks new card
    else:  #will always be suit
        if a != "HEARTS" and a != "SPADES" and a != "DIAMONDS" and a!= "CLUBS":  #check for valid suit
            a = raw_input("This is not a valid suit, please re-enter: ").upper()  #asks for a differant suit
            a = checkCard(a, b)  #checks new number
    return a  #return correct value

#change face cards to numbers
def faceToNumber(card):
    if card == "J":
        card = 11
    if card == "Q":
        card = 12
    if card == "K":
        card = 13
    if card == "A":
        card = 14
    return card

#change number to face card
def numberToFace(card):
    if card == 11:
        return "J"
    if card == 12:
        return "Q"
    if card == 13:
        return "K"
    if card == 14:
        return "A"

#take cards and sort them from highest to lowest
def sort(card1, card2, card3):
    if card1 >= card2:
        switch = card1 #switch used as temporary holding space
        card1 = card2
        card2 = switch
        
    if card2 >= card3:
        switch = card2
        card2 = card3
        card3 = switch
        
    if card1>= card2: #must recheck incase lowest card was entered last
        switch = card1
        card1 = card2
        card2 = switch

    return card1, card2, card3

#test to see if there is a pair
def testPair(rank1, rank2, rank3):
    if rank1 == rank2:
        pair1 = 1
    else: pair1 = 0
    if rank2 == rank3:
        pair2 = 1
    else: pair2=0
    return pair1, pair2

#test to see if it is a straight
def testStraight(rank1, rank2, rank3):
    if rank1 + 1 == rankfile:///usr/share/doc/HTML/index.html2 and rank2 + 1 == rank3:
        return 1
    else: return 0

#test for flush
def testFlush(suit1, suit2, suit3):
    if suit1 == suit2 == suit3:
        return 1
    else: return 0

#displays your hand
def display(hand):
    print "****************************************"
    print "Your are holding a", hand
    print "****************************************"

#end of functions

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
(rank1, rank2, rank3) = sort(rank1, rank2, rank3) #Sort cards from lowest to highest
(pair1, pair2) = testPair(rank1, rank2, rank3)#Test for pair, if rank 1 = 2 pair1 changes, if rank 2=3, pair 2 changes
if pair1 + pair2 == 0: #if there is a pair, there can not be a straight or a flush
    straight = testStraight(rank1, rank2, rank3)#Test for straight, if one exists, straight changes to 1
    flush = testFlush(suit1, suit2, suit3)#Test for flush, if one exists, flush becomes 1
else:
    straight = 0
    flush = 0

#test for each type of hand in order of priority
if flush == 1 and straight == 1:   #straight flush
    display("straight flush")
elif pair1 + pair2 == 2:           #3 of a kind
    display("3 of a kind")
elif straight == 1:                #straight
    display("straight")
elif flush == 1:                   #flush
    display("flush")
elif pair1 + pair2 == 1:           #pair
    display("pair")
else:                              #high card
    if rank3 > 10: #face cards need to be turned back from intergers
        rank3 = numberToFace(rank3)
    print rank3, "high"
