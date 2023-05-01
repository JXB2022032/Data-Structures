# Solitaire-Encryption

Solitaire encryption was designed to be a manual cryptosystem calculated with an ordinary deck of playing cards.

This algorithm uses a standard deck of cards with 52 suited cards and two jokers which are distinguishable from each other, called the A joker and the B joker

## Encode/Decode with Solitaire:

Given a message to encode, we need to first remove all non–letters and convert any lower–case letters to upper–case. We then use the keystream of values and convert each letter to the letter obtained by shifting the original one a certain number of positions to the right on the alphabet. This number is the one found in the keystream in the same position as the character we are encoding.

Decryption (decode) is just the reverse of encryption. Using the same keystream that was used to generate the ciphertext, convert each letter to the letter obtained by shifting the original one the given number of positions to the left on the alphabet.

For example, let’s say that Alice wants to send the following message:

      Is that you, Bob?
Then she will first remove all the non-letters and capitalize all the remaining ones obtaining the following:

      ISTHATYOUBOB
She will then generate a keystream of 12 values. We’ll talk about the keystream generation in the next section, so let’s assume that the keystream is the following:

      11 9 23 7 10 25 11 11 7 8 9 3
Finally, she can generate the ciphertext by shifting each letter the appropriate number of positions to the right in the alphabet. For example, the ‘I’ shifted 11 positions to the right, becomes a ‘T’. The ‘S’ shifted 9 positions to the right becomes a ‘B’. And so on! The final ciphertext will be:

      TBQOKSJZBJXE
Bob, upon receiving the message, will need to generate the keystream. If Alice and Bob shared the same key and used it to generate the same number of pseudorandom values, then the keystream generated in this moment by Bob will be equal to that used by Alice to encrypt the message. All there’s left for Bob to do is convert all the letters by shifting them the appropriate number of position to the left.

## Generating a Keystream Using a Deck of Cards

The idea is to use a deck of playing cards plus two jokers (a red one and a black one). Each card is associated with 4 a value which depends on its rank and its suit.
