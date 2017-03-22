#!/bin/sh

sbt clean 'set test in assembly := {}' assembly
