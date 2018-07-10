PHASE 1 README: group_0131

----
OVERVIEW

This is a simulation of a transit system, whose specifications are outlined in the CSC207:
Project Phase 1 Introduction handout. It takes in a sequence of action lines in a file named
events.txt, processes the given actions, and prints the results of any given action into output.txt.
events.txt can be divided into 2 stages: initialization and program execution. Initialization
constructs the routes in the transit system, which are immutable throughout a given events.txt,
and program execution governs all other actions in the program, including but not limited to
user and card creation, card taps, and report retrieval (these are outlined below under PROGRAM
EXECUTION FUNCTION LIST).

----
INITIALIZATION

This should always be the first few lines in any events.txt file to be executed by the program.
More specifically, the first line of any events.txt should be of the following form, where a is the
number of routes in the transit system, and YYYY-MM-DD is the current date, in
integer form (being sure to preserve the whitespace in this file):

INIT | a | YYYY-MM-DD

If this format is not followed, the program will not run and an error message
will be printed to output.txt.

The next a lines should be initializing each of the routes. The following a lines should be of the
following form, where STATIONTYPE is the type of stations in the given route IN ALL CAPS
(BUS or SUBWAY), and StationName# are the station names in order of their appearance in the given route.

STATIONTYPE | StationName1 | StationName2 | StationName3 | ... | StationNameN |

Note that self-intersecting, or circular, routes are not supported in this system.

The remaining lines of the program are action lines, contributing to program execution.

----
PROGRAM EXECUTION FUNCTION LIST

The following is a list of valid commands that a user can execute in the program execution
section of events.txt. These MUST be in chronological order, or else the command is not executed and
an error message is printed to events.txt.

Each function is given a description, and a usage. All parameters to functions are outlined at the
bottom of this section. Please follow the given strings for lines exactly, including
capitalization and whitespace.

---

TAP: Taps a given card at the station, either entering and exiting a given station.
Tap covers both entrance and exit for the user, the system keeps track of entrance and exit and
handles these cases appropriately. N.B.: for continuous trips, YOU MUST TAP TWICE AT EACH CROSSOVER STOP.
So for example, if you have two routes, one with Station A and Station B and one with Station B and
Station C, if you want a continuous trip from A to C, you must call tap twice at Station B, once to
leave the first route, and once to enter the second route.

Usage:
TAP | HH:MM | cardholderemail@fakemail.io | card# | STATIONTYPE | stationName

--

ADDUSER: Adds a given user to the system, with the given name and email. Also adds admin users to
the transit system.

Usage:
ADDUSER | HH:MM | Admin? | userName | cardholderemail@fakemail.io

--

ADDCARD: Adds a new card to the transit system.

Usage:
ADDCARD | HH:MM | cardholderemail@fakemail.io

--

REMOVECARD: Removes a given card from the transit system. This is different from REPORTTHEFT, which
only deactivates a given card.

Usage:
REMOVECARD | HH:MM | cardholderemail@fakemail.io | card#

--

REPORTTHEFT: Deactivates a given card from the transit system, which can be reactivated
with ACTIVATECARD.

Usage:
REPORTTHEFT | cardholderemail@fakemail.io | card#

--

ACTIVATECARD: Activates a given card from the transit system, having been deactivated with
REPORTTHEFT.

Usage:
ACTIVATECARD | cardholderemail@fakemail.io | card#

--

DAILYREPORTS: Retrieves a daily report of the transit system, taking in a valid admin user's email
as a verification, as only AdminUsers can generate daily reports.

DAILYREPORTS | adminuseremail@fakemail.io |

--

ADDFUNDS: Adds the given amount of funds to a card.

Usage:
ADDFUNDS | HH:MM | cardholderemail@fakemail.io | card# | dollarsAdded

--

CHANGENAME: Changes the userName of the given user.

Usage:
CHANGENAME | cardholderemail@fakemail.io | newName

--

ENDDAY: Ends the given day, and sets the time to the following day. Does NOT
print a dailyReport, but only handles the dateTime adjustment (for daily reports, see DAILYREPORTS)
Note that leap years are not supported in this program.

Usage:
ENDDAY

--

MONTHLYEXPENDITURE: Gets a list of average daily expenditures for each month for a given user.

Usage:
MONTHLYEXPENDITURE | cardholderemail@fakemail.io

--

CHECKBALANCE: Gets the current balance of the given card

Usage:
CHECKBALANCE | cardholderemail@fakemail.io | card#

--

GETLASTTHREE: Reports the last three trips of a given user to output.txt

Usage:
GETLASTTHREE | cardholderemail@fakemail.io

--

USERREPORT: Reports a given user's cards and the current balances on them to output.txt

Usage:
USERREPORT | cardholderemail@fakemail.io

---

Parameters:
- HH:MM is the time at which this card is tapped, in that format (the date is kept independently
  by the system, and is only modified by the ENDDAY command)
- cardholderemail@fakemail.io is the email of the owner of this card
- card# is the unique card number of this user, which are generated as you call ADDCARD (they are
  numbered in order of initialization for a given user). So if you give a user two cards, the first
  card has an id 1, and the second card has an id 2. THIS IS USER-SPECIFIC, so multiple cards in a
  system can have id 1, but these would be owned by different users.
- STATIONTYPE is the type of stations being interacted with. This can be either BUS or STATION, in
  all-caps, and any other system results in an error thrown.
- stationName is the name of the station you are tapping into. Station names are guaranteed to be
  unique within a given type (i.e. you can have stations with the same name of different type, for
  the sake of changing modes of transportation in a given trip)
- Admin? can be yes or no, and determines whether or not the user being created is an admin user
- userName is the username of this user. This can be changed by calling CHANGENAME
- dollarsAdded is the dollar value of funds to add to the given card's balance.
  IN INTEGER FORM ONLY: cent values not accepted
- newName is the new user name to be passed to the system
- adminuseremail@fakemail.io is an AdminUser email (when used as a parameter, it MUST be an AdminUser)

----
REPORT FETCHING

One important functionality of this program is the ability for admin users to generate daily reports
about the simulated transit system. Currently, our program daily reports will report the total amount
of revenue collected from trips taken that day.  The days and their corresponding revenue's are displayed
in chronological order. A sample daily report is given below.

Date       Revenue
2012-08-07 $500.50
2012-08-06 $375.00
2012-08-05 $638.50

----
SAMPLE PROGRAM EXECUTION

