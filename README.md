# MorganTest
Solution to a morgan stanley test problem

The configuration i.e. the direct conversions and indirect conversion information comes from 2 property files
Once the same is read, the indirect conversion table is created, with direct conversion entries(wherever possible). This happens only
once during service initialization, subsequent objects of the service would work on the initially set configuration.

Once the table is created, lookups are made recursively, coming to end when a direct conversion is found. Also each calculated indirect
conversion is re recorded in the table to save subsequent calls processing.
