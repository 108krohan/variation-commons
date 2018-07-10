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

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.rules.ExpectedException;

public class VariantClassifierTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetVariantClassification() throws IllegalArgumentException {
        assertEquals(VariantType.SNV, VariantClassifier.getVariantClassification("A", "C", 1));

        assertEquals(VariantType.DEL, VariantClassifier.getVariantClassification("TT", "", 2));
        assertEquals(VariantType.INS, VariantClassifier.getVariantClassification("", "G", 2));

        assertEquals(VariantType.INDEL, VariantClassifier.getVariantClassification("A", "GTC", 2));
        assertEquals(VariantType.INDEL, VariantClassifier.getVariantClassification("AA", "G", 2));

        assertEquals(VariantType.TANDEM_REPEAT, VariantClassifier.getVariantClassification("(A)5", "(A)7", 4));

        assertEquals(VariantType.SEQUENCE_ALTERATION,
                     VariantClassifier.getVariantClassification("(ALI008)", "(LI090)", 5));
        assertEquals(VariantType.SEQUENCE_ALTERATION, VariantClassifier.getVariantClassification("ATCZ", "", 5));
        assertEquals(VariantType.SEQUENCE_ALTERATION,
                     VariantClassifier.getVariantClassification("(ALI008)", "(LI090)", 5));

        assertEquals(VariantType.NO_SEQUENCE_ALTERATION,
                     VariantClassifier.getVariantClassification("NOVARIATION", "", 6));
        assertNotEquals(VariantType.NO_SEQUENCE_ALTERATION,
                     VariantClassifier.getVariantClassification("NOVAR", "", 6));

        assertEquals(VariantType.MNV, VariantClassifier.getVariantClassification("AT", "CG",
                                                                                   8));


        //Test case where we expect Exceptions to be thrown
        thrown.expect(IllegalArgumentException.class);
        VariantClassifier.getVariantClassification("a", "C", 1);
        thrown.expect(IllegalArgumentException.class);
        VariantClassifier.getVariantClassification("AA", "-", 2);
        thrown.expect(IllegalArgumentException.class);
        VariantClassifier.getVariantClassification("NOVARIATION", "", 5);
    }

}
