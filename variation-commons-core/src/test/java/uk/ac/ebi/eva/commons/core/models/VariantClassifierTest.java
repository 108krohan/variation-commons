/*
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.ebi.eva.commons.core.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class VariantClassifierTest {

    @Test
    public void testSNVs() {
        assertEquals(VariantType.SNV, VariantClassifier.getVariantClassification("A", "C", 1));
    }

    @Test
    public void testDeletions() {
        assertEquals(VariantType.DEL, VariantClassifier.getVariantClassification("TT", "", 2));
    }

    @Test
    public void testInsertions() {
        assertEquals(VariantType.INS, VariantClassifier.getVariantClassification("", "G", 2));
    }

    @Test
    public void testINDELs() {
        assertEquals(VariantType.INDEL, VariantClassifier.getVariantClassification("A", "GTC", 2));
        assertEquals(VariantType.INDEL, VariantClassifier.getVariantClassification("AA", "G", 2));
    }

    @Test
    public void testSTRs() {
        assertEquals(VariantType.TANDEM_REPEAT, VariantClassifier.getVariantClassification("(A)5", "(A)7", 4));
        assertEquals(VariantType.TANDEM_REPEAT, VariantClassifier.getVariantClassification("AAAAA", "AAAAAAA", 4));
    }

    @Test
    public void testSequenceAlterations() {
        assertEquals(VariantType.SEQUENCE_ALTERATION,
                     VariantClassifier.getVariantClassification("(ALI008)", "(LI090)", 5));
        assertEquals(VariantType.SEQUENCE_ALTERATION, VariantClassifier.getVariantClassification("ATCZ", "", 5));
        assertEquals(VariantType.SEQUENCE_ALTERATION,
                     VariantClassifier.getVariantClassification("(ALI008)", "(LI090)", 5));
    }

    @Test
    public void testNoSequenceAlterations() {
        assertEquals(VariantType.NO_SEQUENCE_ALTERATION,
                     VariantClassifier.getVariantClassification("NOVARIATION", "", 6));
        assertNotEquals(VariantType.NO_SEQUENCE_ALTERATION,
                        VariantClassifier.getVariantClassification("NOVAR", "", 6));
        assertEquals(VariantType.NO_SEQUENCE_ALTERATION,
                     VariantClassifier.getVariantClassification("", "", 6));
        assertEquals(VariantType.NO_SEQUENCE_ALTERATION,
                     VariantClassifier.getVariantClassification("NOVARIATION", "NOVARIATION", 6));
    }

    @Test
    public void testMNVs() {
        assertEquals(VariantType.MNV, VariantClassifier.getVariantClassification("AT", "CG", 8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHyphensInReference() {
        VariantClassifier.getVariantClassification("-", "GG", 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHyphensInAlternate() {
        VariantClassifier.getVariantClassification("AA", "-", 2);
    }
}
