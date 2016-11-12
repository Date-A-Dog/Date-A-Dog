package dateadog.dateadog;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@code DADAPI} interfaces with the Date-A-Dog server and the Petfinder API to retrieve and
 * update information about the user and dogs available to them.
 */
public class DADAPI {

    private static final String BACKEND_DATA = "[\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"8697861\",\n" +
            "      \"age\": \"Senior\",\n" +
            "      \"mix\": \"yes\",\n" +
            "      \"sex\": \"F\",\n" +
            "      \"name\": \"Honey Bear\",\n" +
            "      \"size\": \"M\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/8697861/1/?bust=1460409799&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/8697861/1/?bust=1460409799&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/8697861/1/?bust=1460409799&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/8697861/1/?bust=1460409799&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/8697861/1/?bust=1460409799&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/8697861/2/?bust=1460409799&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/8697861/2/?bust=1460409799&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/8697861/2/?bust=1460409799&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/8697861/2/?bust=1460409799&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/8697861/2/?bust=1460409799&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"3\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/8697861/3/?bust=1460409799&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/8697861/3/?bust=1460409799&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/8697861/3/?bust=1460409799&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/8697861/3/?bust=1460409799&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/8697861/3/?bust=1460409799&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Pit Bull Terrier\",\n" +
            "        \"Bull Terrier\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98062\",\n" +
            "        \"city\": \"Seahurst\",\n" +
            "        \"email\": \"info@myaarf.org\",\n" +
            "        \"state\": \"WA\",\n" +
            "        \"address1\": \"PO Box 328\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA355\",\n" +
            "      \"description\": \"Honey Bear is an incredibly sweet, playful and charismatic pit bull. She is approximately 8-9 yrs old. She would do great in a house or condo as she has a low energy level and would be fine with a short walk once a day or every other. She is dog selective and sometimes reactive towards other dogs on walks. Honey Bear thoroughly enjoys cuddle with her foster family. Honey Bear is 100% kennel trained for when her foster family is at work and does not show any signs of separation anxiety. Honey Bear is also potty trained. When you tell her it's time to 'Go' she gets very excited. Honey Bear knows sit and stay. She is looking forward to playing in a yard of her own. She is full of love and has so much joy to share! Honey Bear is too interested in cats though, so no cats please!  She is getting arthritic and is on pain meds and glucosamine\\n\\nIf you think Honey Bear might be a good match for your family please fill in our online application at www.myaarf.org As we are purely a foster based rescue with our dogs all living in homes with busy foster families we are unable to honor requests to meet our pups without a pre approved application on file.\\n\\nIf you have any questions, please do not hesitate to ask! $225 adoption fee applies. All our dogs are vaccinated, microchipped and spayed/neutered. The first 30 days after adoption is considered a trial period, if for any reason you return the dog in the trial period your adoption fee is 75% refundable.\\n***Our email responses FREQUENTLY are marked as 'SPAM', so if you don't receive a response from us within 24-48 hours, please check your spam folder. Thank you!***\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"12965366\",\n" +
            "      \"age\": \"Young\",\n" +
            "      \"mix\": \"yes\",\n" +
            "      \"sex\": \"M\",\n" +
            "      \"name\": \"**FOSTERS NEEDED**\",\n" +
            "      \"size\": \"S\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/12965366/1/?bust=1459847551&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/12965366/1/?bust=1459847551&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/12965366/1/?bust=1459847551&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/12965366/1/?bust=1459847551&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/12965366/1/?bust=1459847551&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Terrier\",\n" +
            "        \"Chihuahua\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"fax\": \"206-902-5229\",\n" +
            "        \"zip\": \"98026\",\n" +
            "        \"city\": \"Edmonds\",\n" +
            "        \"email\": \"info@nwipr.org\",\n" +
            "        \"phone\": \"Email Please  \",\n" +
            "        \"state\": \"WA\",\n" +
            "        \"address1\": \"WA Foster Care Homes\",\n" +
            "        \"address2\": \"PO Box 5531, Lynnwood, WA 98046\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA427\",\n" +
            "      \"description\": \"\\n IMMEDIATE FOSTER NEEDS\\n\\nWe have several small to medium size dogs we want to save, however we need more foster homes to do so.  Can you help? \\n\\n\\n\\nNWIPR is always in need of loving, caring, and committed foster homes.\\n The more fosters, the more pets we can save. Our foster homes are the backbone to our organization!  You will care for and socialize the pet while that pet's ideal, forever home is found. You may also be involved in some of the transport functions (e.g., vet, groomer, training facility).  NWIPR's fosters are the pet's advocate and will help select the most suitably matched home for the pet's general and unique needs.  Your insight, evaluation, and care are essential in finding the proper home for your foster pet.\\n\\n\\n\\nWe work mainly with local shelters, other dog rescue organizations and individuals, to help these wonderful and deserving pets. Without foster homes we cannot save dogs. We do not have a facility; we are solely a foster home program. \\n\\n\\n\\nIn addition, NWIPR will cover all approved veterinary care of the foster pet.  If you are in need of support on food, please let us know so we can accommodate your needs.  \\n\\n\\n\\nThank you very much for considering to be a foster home.  \\nYour assistance is greatly appreciated.\\n\\n\\nWE ARE A NON-PROFIT 501c3 ORGANIZATION AND A REGISTERED WA STATE CHARITY  \\n\\n\\n\\n\\n**Please complete the Volunteer Application to begin helping**\\n \\n\\n\\n\\n\\\"Saving just one pet won't change the world ... but, surely, the world will change for that one pet\\\" author unknown\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\",\n" +
            "      \"shelterPetId\": \"FOSTERS NEEDED\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"13194758\",\n" +
            "      \"age\": \"Baby\",\n" +
            "      \"mix\": \"yes\",\n" +
            "      \"sex\": \"F\",\n" +
            "      \"name\": \"Foster Homes Needed!\",\n" +
            "      \"size\": \"M\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/13194758/1/?bust=1291034096&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/13194758/1/?bust=1291034096&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/13194758/1/?bust=1291034096&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/13194758/1/?bust=1291034096&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/13194758/1/?bust=1291034096&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Chihuahua\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98045\",\n" +
            "        \"city\": \"North Bend\",\n" +
            "        \"email\": \"lynn.dillon65@gmail.com\",\n" +
            "        \"state\": \"WA\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA429\",\n" +
            "      \"description\": \"Our rescue takes in death row dogs who have run out of time due to overcrowded shelters...we really need volunteer foster homes to save these dogs from death...please contact us to help.  We can' t save them without your help....please HELP!!!\\n\\nLove A Mutt would like you to join our team of volunteers and help foster a dog.  We need more foster homes in order to help more dogs in need.  Thank you for considering saving a pet and becoming a volunteer.  It is rewarding to see them find forever homes.  As a volunteer you become the pets advocate that you are fostering.\\n\\nEmail lynn.dillon65@gmail.com and we will send you the foster information.\\n\\nYou provide a safe, loving, warm home and provide guidance. We provide food, transportation and all vet costs are paid by the rescue. \\n\\nPLEASE HELP FOSTER AN ABANDONED ANIMAL\\nHELP NEEDED FOR ALL SIZES/BREEDS OF DOGS\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"18083295\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"yes\",\n" +
            "      \"sex\": \"M\",\n" +
            "      \"name\": \"Axel\",\n" +
            "      \"size\": \"M\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/18083295/1/?bust=1291675490&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/18083295/1/?bust=1291675490&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/18083295/1/?bust=1291675490&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/18083295/1/?bust=1291675490&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/18083295/1/?bust=1291675490&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/18083295/2/?bust=1291675490&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/18083295/2/?bust=1291675490&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/18083295/2/?bust=1291675490&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/18083295/2/?bust=1291675490&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/18083295/2/?bust=1291675490&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Hound\",\n" +
            "        \"Labrador Retriever\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98003\",\n" +
            "        \"city\": \"Federal Way\",\n" +
            "        \"email\": \"bffurever@yahoo.com\",\n" +
            "        \"phone\": \"713-859-7415\",\n" +
            "        \"state\": \"WA\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"TX1156\",\n" +
            "      \"description\": \"AXEL was abandoned a a local nature park. A lab mix, he has an old break in his back leg, probably from being hit by a car...A sweet boy, he is good with other dogs and loves people...His leg injury does not seem to bother him, and the vet recommends leaving it as is. AXEL is about 8 yrs. old, waiting patiently for his furever home. He is crate trained, and is working on his house training...What a great boy!\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"21440797\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"yes\",\n" +
            "      \"sex\": \"F\",\n" +
            "      \"name\": \"Jolie - A Joyous Girl\",\n" +
            "      \"size\": \"M\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/21440797/1/?bust=1338332047&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/21440797/1/?bust=1338332047&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/21440797/1/?bust=1338332047&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/21440797/1/?bust=1338332047&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/21440797/1/?bust=1338332047&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/21440797/2/?bust=1338332057&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/21440797/2/?bust=1338332057&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/21440797/2/?bust=1338332057&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/21440797/2/?bust=1338332057&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/21440797/2/?bust=1338332057&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"3\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/21440797/3/?bust=1338332067&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/21440797/3/?bust=1338332067&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/21440797/3/?bust=1338332067&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/21440797/3/?bust=1338332067&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/21440797/3/?bust=1338332067&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Labrador Retriever\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98003\",\n" +
            "        \"city\": \"Federal Way\",\n" +
            "        \"email\": \"bffurever@yahoo.com\",\n" +
            "        \"phone\": \"713-859-7415\",\n" +
            "        \"state\": \"WA\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"TX1156\",\n" +
            "      \"description\": \"JOLIE is a beautiful girl with a personality to match...She, her sisters, and mom were starving to death in the middle of nowhere...They just happened to be discovered by an off-road biker...About 5 years old, she is a little shy at first, but what a lovebug once she knows you! She just needs a chance to know what a warm, loving family can be!\\n\\nEmail bffurever@yahoo.com today!\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"22143035\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"yes\",\n" +
            "      \"sex\": \"M\",\n" +
            "      \"name\": \"Dominic\",\n" +
            "      \"size\": \"M\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/22143035/1/?bust=1463908003&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/22143035/1/?bust=1463908003&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/22143035/1/?bust=1463908003&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/22143035/1/?bust=1463908003&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/22143035/1/?bust=1463908003&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/22143035/2/?bust=1463908004&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/22143035/2/?bust=1463908004&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/22143035/2/?bust=1463908004&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/22143035/2/?bust=1463908004&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/22143035/2/?bust=1463908004&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"3\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/22143035/3/?bust=1463908004&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/22143035/3/?bust=1463908004&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/22143035/3/?bust=1463908004&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/22143035/3/?bust=1463908004&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/22143035/3/?bust=1463908004&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Labrador Retriever\",\n" +
            "        \"Terrier\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98003\",\n" +
            "        \"city\": \"Federal Way\",\n" +
            "        \"email\": \"bffurever@yahoo.com\",\n" +
            "        \"phone\": \"713-859-7415\",\n" +
            "        \"state\": \"WA\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"TX1156\",\n" +
            "      \"description\": \"Sweet DOMINIC was about to lose his life in a dirty, dingy, city shelter. We couldn't let that happen! He is very gentle,loving, and loyal to his people. He is learning to walk on leash and pay attention to what you are saying. DOM does well in his crate and does not create a fuss. DOMINIC has a slight vision impairment in one eye which isn't really much of a hinderance to him - the vet thinks he can see shadows. HIs ideal home would be a loving family, maybe even with a girlfriend to keep him company! DOM is about 6 years old and 60 pounds. Please email ppace@mac.com for more information on this super boy!\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"22406048\",\n" +
            "      \"age\": \"Young\",\n" +
            "      \"mix\": \"no\",\n" +
            "      \"sex\": \"F\",\n" +
            "      \"name\": \"DONATIONS: BEDS & TOYS\",\n" +
            "      \"size\": \"L\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/22406048/1/?bust=1430749565&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/22406048/1/?bust=1430749565&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/22406048/1/?bust=1430749565&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/22406048/1/?bust=1430749565&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/22406048/1/?bust=1430749565&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"German Shepherd Dog\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"fax\": \"www.washingtongsd.org\",\n" +
            "        \"zip\": \"98104\",\n" +
            "        \"city\": \"Seattle\",\n" +
            "        \"email\": \"washingtonshepherds@yahoo.com\",\n" +
            "        \"phone\": \"(206) 445-5151\",\n" +
            "        \"state\": \"WA\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA214\",\n" +
            "      \"description\": \"Washington German Shepherd Rescue is always in need of dog beds and toys for our rescue dogs in new foster homes.\\n\\nWe accept new and used beds.  Dog beds from Costco, typically $29.99 in store, are economical and the perfect size for this large breed.  We also need dog toys for those rescue dogs that like to chew and play and have fun! We accept new and used toys.\\n\\nIf you would like to donate to the Washington German Shepherd Rescue, please visit our website and send us an email today!\\n\\nhttp://www.washingtongsd.org/\\n\\nAre you following us on Facebook? Our Facebook account is updated daily with information about our rescue dogs and volunteer opportunities!\\n\\nhttps://www.facebook.com/WashingtonGermanShepherdRescue\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"22650738\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"no\",\n" +
            "      \"sex\": \"M\",\n" +
            "      \"name\": \"Volunteer!  Foster Homes Needed\",\n" +
            "      \"size\": \"S\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/22650738/1/?bust=1459863059&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/22650738/1/?bust=1459863059&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/22650738/1/?bust=1459863059&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/22650738/1/?bust=1459863059&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/22650738/1/?bust=1459863059&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/22650738/2/?bust=1459863059&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/22650738/2/?bust=1459863059&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/22650738/2/?bust=1459863059&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/22650738/2/?bust=1459863059&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/22650738/2/?bust=1459863059&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"3\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/22650738/3/?bust=1459863059&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/22650738/3/?bust=1459863059&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/22650738/3/?bust=1459863059&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/22650738/3/?bust=1459863059&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/22650738/3/?bust=1459863059&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Miniature Pinscher\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98404\",\n" +
            "        \"city\": \"Tacoma\",\n" +
            "        \"email\": \"wdapllama@aol.com\",\n" +
            "        \"phone\": \"Email Please\",\n" +
            "        \"state\": \"WA\",\n" +
            "        \"address1\": \"Tacoma, WA\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA28\",\n" +
            "      \"description\": \"PLEASE HELP! The dogs pictured here have already found their forever homes, but we are desperate for foster homes now so that we can save others. Foster Homes are the key to being able to save lives, if we do not have enough Foster Homes, dogs may be turned away.  You can do your share by fostering for a few weeks or a few months, while our team of volunteers works tirelessly at finding our dogs permanent homes.  As a foster parent, you are asked to provide one of our lovely dogs with a loving home while we try to find it a forever family. As a foster, you are responsible for feeding, exercising, loving, protecting, caring and cuddling a beautiful little one. IMPS is responsible for all the dogâ\u0080\u0099s veterinary expenses. IF you have the time and patience, you are encouraged to teach the dog basic behavior and commands, but most of our dogs, unless very young, already have these.  Be prepared to fall in love!   All the other cats/dogs in the home must be spayed/neutered and up to date with shots.  In the case of very small dogs, it is preferable that there are no children under 10 years old.  Expect to feel so good and accomplished as your foster leaves to join their new family, it can be hard too, but all the other Fosters are here to support you, and there will be another furry face waiting for your help!  Fill out a volunteer application today and one of our volunteers will be in touch shortly!  http://minpinrescue.org/vol_app1.html\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"23792649\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"yes\",\n" +
            "      \"sex\": \"F\",\n" +
            "      \"name\": \"CUPCAKE (Pearl)- BEST FRIEND\",\n" +
            "      \"size\": \"S\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/23792649/1/?bust=1464779111&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/23792649/1/?bust=1464779111&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/23792649/1/?bust=1464779111&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/23792649/1/?bust=1464779111&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/23792649/1/?bust=1464779111&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/23792649/2/?bust=1464779112&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/23792649/2/?bust=1464779112&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/23792649/2/?bust=1464779112&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/23792649/2/?bust=1464779112&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/23792649/2/?bust=1464779112&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"3\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/23792649/3/?bust=1464779112&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/23792649/3/?bust=1464779112&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/23792649/3/?bust=1464779112&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/23792649/3/?bust=1464779112&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/23792649/3/?bust=1464779112&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Border Terrier\",\n" +
            "        \"Dachshund\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"fax\": \"281-331-1702\",\n" +
            "        \"zip\": \"98101\",\n" +
            "        \"city\": \"Seattle\",\n" +
            "        \"email\": \"NABSTX2004@AOL.COM\",\n" +
            "        \"phone\": \"281-330-5238\",\n" +
            "        \"state\": \"WA\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\",\n" +
            "        \"noCats\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"TX719\",\n" +
            "      \"description\": \"Hi, my name now is CUPCAKE.\\nI arrived at N.A.B.S. after I was impounded at a high kill local shelter with my 4 sisters. I was adopted and for several years I got lost but my adopter was always found. This last time I was found in the streets with no collar, just a piece of rope around my neck and my owner had been not found. I came back to N.A.B.S. hoping now I can find a forever home. \\nWhen I was a puppy I was named Pearl but last name my foster knew I had was Cupcake. I am approx. 4 years old. I seem good with other dogs but NOT with cats. I am good walking in a leash and I am cage trained.\\nPLEASE WATCH MY SHORT VIDEO :)\\nShe is located in HOUSTON area, TX and my adoption fee is $400 and it will include all my shots, micro-chipped, neutering, health certificate and transport with ground rescue transport (airplane transport will be $600)\\nShe is been in monthly flea and Heartworm preventive. \\nIf you are interested on her and you have any question please do not hesitate to email nabstx2004@aol.com or call or text 281-330-5238. \\nYou can also submit an online adoption application for me here:  \\nhttp://www.nabs-tx.com/adoption-application.html\\nand we will contact you ASAP\\nThank you for consider adopting a rescued pet.\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"24710041\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"no\",\n" +
            "      \"sex\": \"M\",\n" +
            "      \"name\": \"FOSTERS NEEDED\",\n" +
            "      \"size\": \"L\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/24710041/1/?bust=1353581363&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/24710041/1/?bust=1353581363&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/24710041/1/?bust=1353581363&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/24710041/1/?bust=1353581363&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/24710041/1/?bust=1353581363&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"German Shepherd Dog\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98012\",\n" +
            "        \"city\": \"Mill Creek\",\n" +
            "        \"email\": \"info@northwestgermanshepherd.org\",\n" +
            "        \"state\": \"WA\",\n" +
            "        \"address1\": \"PO BOX 13224\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA422\",\n" +
            "      \"description\": \"URGENT PLEASE . . . . . WE NEED FOSTER HOMES!!\\nIn order to find loving forever homes for these wonderful dogs, we need foster homes.  Foster homes are key to the success of these dogs . . . . . without you they have no place to go, no hope for a future.  And this is especially true though the holiday season which can be a time of real stress for them.\\nPlease help them be safe for the short time they need until we find their forever homes.  The holidays are for giving . . . . . let's give these dogs the gift of a new chance for happiness!  If you think you can help, even for a short time, please visit our website to fill out a foster application\\nhttp://www.northwestgermanshepherd.org\\nThank you and we wish you all a wonderful holiday season\\nThe volunteers with Northwest German Shepherd Rescue\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"25447517\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"no\",\n" +
            "      \"sex\": \"F\",\n" +
            "      \"name\": \"We Are Looking For Fosters!!!\",\n" +
            "      \"size\": \"S\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/25447517/1/?bust=1458899864&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/25447517/1/?bust=1458899864&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/25447517/1/?bust=1458899864&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/25447517/1/?bust=1458899864&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/25447517/1/?bust=1458899864&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/25447517/2/?bust=1458899864&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/25447517/2/?bust=1458899864&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/25447517/2/?bust=1458899864&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/25447517/2/?bust=1458899864&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/25447517/2/?bust=1458899864&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"3\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/25447517/3/?bust=1458899864&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/25447517/3/?bust=1458899864&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/25447517/3/?bust=1458899864&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/25447517/3/?bust=1458899864&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/25447517/3/?bust=1458899864&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Chihuahua\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98370\",\n" +
            "        \"city\": \"Poulsbo\",\n" +
            "        \"email\": \"Dixie@BlindDogHaven.com\",\n" +
            "        \"phone\": \"360-509-9808\",\n" +
            "        \"state\": \"WA\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA351\",\n" +
            "      \"description\": \"While puppies and toy breeds, such as chiâ\u0080\u0099s, easily steal our hearts, people can forget they are a living thing that requires a lifetime commitment.  So many of these little ones are being surrendered daily in shelters, and while they walk in â\u0080\u0093 too few ever leave the same way.  We work with high kill shelters and rescue as many of these darlings we canâ\u0080¦both healthy and those with special needs.  We desperately need more fosters to help provide a home, instead of a cage, for these sweeties to acclimate while awaiting permanent placement.   Unlike other rescue organizations, we do allow our fosters to adopt so it is a great way to see if this dog is actually right for you and your family.   Please email or call and let us answer your questions.  It only takes a moment to change your lifeâ\u0080¦and the life of another.\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"25853583\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"no\",\n" +
            "      \"sex\": \"M\",\n" +
            "      \"name\": \"Vyse\",\n" +
            "      \"size\": \"M\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/25853583/1/?bust=1449244682&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/25853583/1/?bust=1449244682&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/25853583/1/?bust=1449244682&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/25853583/1/?bust=1449244682&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/25853583/1/?bust=1449244682&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/25853583/2/?bust=1449244683&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/25853583/2/?bust=1449244683&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/25853583/2/?bust=1449244683&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/25853583/2/?bust=1449244683&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/25853583/2/?bust=1449244683&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"3\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/25853583/3/?bust=1449244683&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/25853583/3/?bust=1449244683&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/25853583/3/?bust=1449244683&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/25853583/3/?bust=1449244683&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/25853583/3/?bust=1449244683&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Pit Bull Terrier\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98418\",\n" +
            "        \"city\": \"Tacoma\",\n" +
            "        \"email\": \"drawsdogs@gmail.com\",\n" +
            "        \"state\": \"WA\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"specialNeeds\",\n" +
            "        \"hasShots\",\n" +
            "        \"noDogs\",\n" +
            "        \"altered\",\n" +
            "        \"noCats\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA523\",\n" +
            "      \"description\": \"All dogs are spayed/neutered prior to adoption. They are also microchipped, fully vaccinated and treated for parasites. Adoption fees vary from 50$ to 350$. Application and home check required prior to adoption.\\n\\nVyse is a handsome, 5 year old Pit Bull. He is a really nice, well behaved boy. An easy keeper. Would be best as an only pet but would love a family with kids. He is the perfect companion for an active family who likes movie nights in front of the tv. He is housebroken, crate trained, leash trained and knows basic commands. And the icing on the cake is his unique and beautiful coloring. He does have some pretty serious skin issues that in spite of treatment, we have never been able to completely rid him of and he is prone to occasional flare ups especially when the seasons change. A grain free diet and being kept on an allergy medication has been helpful and we can recommend the foods that have worked best. . But is otherwise a healthy boy. Adoption fee $150.\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"26572764\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"yes\",\n" +
            "      \"sex\": \"M\",\n" +
            "      \"name\": \"FOSTER HOMES NEEDED\",\n" +
            "      \"size\": \"L\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {}\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Black Labrador Retriever\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"fax\": \"425-831-2720\",\n" +
            "        \"zip\": \"98065\",\n" +
            "        \"city\": \"Snoqualmie\",\n" +
            "        \"email\": \"canineconnectionsrescue@gmail.com\",\n" +
            "        \"phone\": \"425-831- 2578\",\n" +
            "        \"state\": \"WA\",\n" +
            "        \"address1\": \"33010 SE 99th Street\"\n" +
            "      },\n" +
            "      \"options\": [],\n" +
            "      \"shelterId\": \"WA23\",\n" +
            "      \"description\": \"Occasionally we are in need of a foster home. This could be done for a weekend or a couple of weeks to however long it takes to find a home. This would be your choice. The weekend care is for the dogs who are waiting for a home and all their fellow rescue dogs are on visits. We don't like to leave anyone alone.  Also for those dogs that have not been adopted and the training session is over. We like to get them out of the kennel setting once they have completed the 8 week session. If you think you would like a weekend visitor or one for a few weeks, please give us a call.  Echo Glen Children's Center is a youth correctional facility located approximately 30 minutes east of Seattle. Canine Connections is a program that pairs the at-risk youth at Echo Glen with dogs that have not yet found successful placements. In this program the youth are taught how to train the dogs in basic obedience through a reward based system.  At the end of its training each dog will complete the AKC Canine Good Citizen and basic Obedience Test.   Please note that our adoption fee is $200.  Please call 425-831-2717 or E-mail canineconnectionsrescue@gmail.com ;to schedule an appointment to see.\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"27451480\",\n" +
            "      \"age\": \"Young\",\n" +
            "      \"mix\": \"yes\",\n" +
            "      \"sex\": \"F\",\n" +
            "      \"name\": \"ADOPTION EVENT! MUD BAY ISSAQUAH!\",\n" +
            "      \"size\": \"S\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/27451480/1/?bust=1380722286&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/27451480/1/?bust=1380722286&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/27451480/1/?bust=1380722286&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/27451480/1/?bust=1380722286&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/27451480/1/?bust=1380722286&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Chihuahua\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98027\",\n" +
            "        \"city\": \"Issaquah\",\n" +
            "        \"phone\": \".\",\n" +
            "        \"state\": \"WA\",\n" +
            "        \"address1\": \"PO Box 1691\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA307\",\n" +
            "      \"description\": \"Please join us for our next Adoption Event\\non Saturday, Novermber 19th from 11:30 - 2:00pm!   \\n\\nMud Bay\\n1590 NW Gilman Blvd,\\nIssaquah, WA 98027\\n\\nSome of our dogs posted online may be available for you to meet. If you're interested in a specific dog, please fill out an application now for pre-approval and contact us to ensure the dog will be there. An approved APPLICATION will be required prior to taking your new dog home. Please feel free to send an application in today and we will process it as soon as possible. Although you will also have the opportunity to fill out an application at the event, you will need pre-approval to complete an adoption.\\n\\nIf you already have an approved application on file with PUP, please email our adoption coordinator Trish , to let her know which dog you are interested in meeting so she can schedule a meeting time.\\n\\nWe look forward to seeing you there!\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"27573517\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"no\",\n" +
            "      \"sex\": \"M\",\n" +
            "      \"name\": \"Hans\",\n" +
            "      \"size\": \"M\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/27573517/1/?bust=1405253478&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/27573517/1/?bust=1405253478&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/27573517/1/?bust=1405253478&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/27573517/1/?bust=1405253478&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/27573517/1/?bust=1405253478&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/27573517/2/?bust=1405253478&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/27573517/2/?bust=1405253478&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/27573517/2/?bust=1405253478&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/27573517/2/?bust=1405253478&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/27573517/2/?bust=1405253478&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"3\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/27573517/3/?bust=1405253479&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/27573517/3/?bust=1405253479&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/27573517/3/?bust=1405253479&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/27573517/3/?bust=1405253479&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/27573517/3/?bust=1405253479&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Pit Bull Terrier\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98418\",\n" +
            "        \"city\": \"Tacoma\",\n" +
            "        \"email\": \"drawsdogs@gmail.com\",\n" +
            "        \"state\": \"WA\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"noDogs\",\n" +
            "        \"altered\",\n" +
            "        \"noCats\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA523\",\n" +
            "      \"description\": \"Please contact Violet (drawsdogs@gmail.com) for more information about this pet.\\n\\nAll dogs are spayed/neutered prior to adoption. They are also microchipped, fully vaccinated and treated for parasites. Adoption fees vary from 50$ to 350$. Application and home check required prior to adoption.\\n\\n\\n\\nHans is a 4 year old Pit Bull. He is an incredibly sweet dog with humans of all ages. He loves to cuddle and be loved on. He can get wound up and be a complete goon. Hans reacts quickly to other dogs and would best be suited as an only dog. But as sweet and cuddly as he is, who needs more than one? Hans knows sit and is prett decent on a leash once he calms down. He loves car rides. He is crate trained and housebroken. Adoption fee $150\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"28211963\",\n" +
            "      \"age\": \"Young\",\n" +
            "      \"mix\": \"no\",\n" +
            "      \"sex\": \"F\",\n" +
            "      \"name\": \"FOSTERS NEEDED!!!\",\n" +
            "      \"size\": \"S\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28211963/1/?bust=1388698494&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28211963/1/?bust=1388698494&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28211963/1/?bust=1388698494&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28211963/1/?bust=1388698494&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28211963/1/?bust=1388698494&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Chihuahua\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98027\",\n" +
            "        \"city\": \"Issaquah\",\n" +
            "        \"phone\": \".\",\n" +
            "        \"state\": \"WA\",\n" +
            "        \"address1\": \"PO Box 1691\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA307\",\n" +
            "      \"description\": \"Foster Homes Urgently Needed! PUP is always in need of loving, caring, and committed foster homes. The more fosters, the more dogs we can pull and rehome. As a PUP foster, you would care for and socialize the dog while that dog's ideal, permanent home is found. You may also be involved in some of the transport functions (e.g., vet, groomer, training facility), as well as the adoption process. PUP pays for all approved food, accessories, and vetting expenses. We are a non-profit, small/toy breed dog rescue service organization. We work locally and nationally with shelters, other dog rescue organizations and individuals, to help these wonderful dogs. Some of the dogs may never have had a kind word spoken to them, a gentle gesture shown to them, a healthy meal given to them, or a visit to a vet to find out what was wrong with them. We need you and our dogs need you! Without our foster homes we cannot save dogs. We do not have a facility; we are solely a foster home program. Our foster volunteers are truly the backbone of PUP. Their participation is critical and very much appreciated. Why become a PUP foster? Because you love dogs and would like to surround yourself with other people who share the same passion. PUP fosters and their families are an amazing group of people and we are so grateful for each and every one of them! If you are interested in becoming a foster for PUP, please visit our Volunteer page to download an online application: PUP Volunteers\\n\\nComplete and email to: volunteer@pupdogrescue.org\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"28496031\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"no\",\n" +
            "      \"sex\": \"M\",\n" +
            "      \"name\": \"Harlan\",\n" +
            "      \"size\": \"S\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28496031/1/?bust=1421154309&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28496031/1/?bust=1421154309&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28496031/1/?bust=1421154309&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28496031/1/?bust=1421154309&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28496031/1/?bust=1421154309&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28496031/2/?bust=1421154309&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28496031/2/?bust=1421154309&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28496031/2/?bust=1421154309&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28496031/2/?bust=1421154309&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28496031/2/?bust=1421154309&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"3\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28496031/3/?bust=1421154309&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28496031/3/?bust=1421154309&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28496031/3/?bust=1421154309&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28496031/3/?bust=1421154309&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28496031/3/?bust=1421154309&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Italian Greyhound\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98117\",\n" +
            "        \"city\": \"Northwest\",\n" +
            "        \"email\": \"igrfadopt@gmail.com\",\n" +
            "        \"state\": \"WA\",\n" +
            "        \"address1\": \"PO Box 30644\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA316\",\n" +
            "      \"description\": \"Please see Harlan on our website!! http://www.itgreyhoundnw.org/harlan.html\\n\\nHarlan is a sweet 8 year old, smaller IG who is a little rough around the edges.  Harlan came to us via an Oregon shelter last summer after he was found wandering the streets for a long period of time.  Like many IGs, he loves to go to iggy play dates and to play with his foster sisters.  Harlan isnâ\u0080\u0099t fond of big dogs, bur rather dogs his own size.  Harlan enjoys outings and walks and loves snuggling in bed at night.  His foster mom says heâ\u0080\u0099s quite the entertainer and will walk on his hind legs.  He also knows how to shake hands. \\n\\nHarlanâ\u0080\u0099s foster mom describes him as a â\u0080\u009Cdiamond in the roughâ\u0080\u009D and has seen many positive changes since he came into rescue.  Harlan is not quick to trust.  When he first came to rescue, he was afraid of men, but now allows his foster dad to pick him up and will sleep with him.  He lets his foster mom put her face close to hisâ\u0080\u0094something that he was afraid of when he first came to rescue. \\n\\nHarlan will not do well with kids because they are too unpredictable and he will try to nip when heâ\u0080\u0099s scared.  Harlan does have occasional seizures, but is not on medication for them. He eats soft food as he lost many of his upper teeth to a dental.  He hadnâ\u0080\u0099t had good vet care and was probably mistreated before he came to rescue.  He eats a higher calorie diet to keep his weight up.\\n\\nWhen Harlan doesnâ\u0080\u0099t feel safe, he barks a lot, but quiets down when offered the chance to go into his crate (his â\u0080\u009Csafe placeâ\u0080\u009D) when this happens. He is also a champion â\u0080\u009Cmarkerâ\u0080\u009D so his foster mom keeps him in a belly band.  Potty habits are still a work in progress.  His marking behavior is undoubtedly due to his fear and anxiety from things that happened in the past.\\n\\nHarlan follows his foster mom around the house and loves to sleep on her feet.  Heâ\u0080\u0099s made significant strides and his â\u0080\u009Cforeverâ\u0080\u009D home will need to be kind and patient and understand that heâ\u0080\u0099s not quite the typical easy going IG.\\n\\nHis foster mom says, â\u0080\u009CHe is a very special guy who has come along way.  I see him change all the time as time goes on and he realizes no one is going to hurt him againâ\u0080\u009D. \\n\\nHarlan is being fostered in Seattle, Wa. Washington/Oregon residents who want to begin the process to adopt an IGCA Rescue dog, please write - igrfadopt@gmail.com to learn about the IGCA adoption process and receive an application, which may also be downloaded here - http://www.itgreyhoundnw.org/uploads/2/1/2/1/21215778/igca-nw_rescue_app.doc . We are always willing to consider placing a dog out of state, but applicants are asked to apply to and work with us through their local IGCA Rep - http://www.italiangreyhound.org/pages/500rescue.html#reps.\",\n" +
            "      \"shelterPetId\": \"Harlan\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"28536111\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"yes\",\n" +
            "      \"sex\": \"M\",\n" +
            "      \"name\": \"Kit\",\n" +
            "      \"size\": \"L\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28536111/1/?bust=1392319300&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28536111/1/?bust=1392319300&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28536111/1/?bust=1392319300&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28536111/1/?bust=1392319300&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28536111/1/?bust=1392319300&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28536111/2/?bust=1392319300&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28536111/2/?bust=1392319300&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28536111/2/?bust=1392319300&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28536111/2/?bust=1392319300&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28536111/2/?bust=1392319300&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"3\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28536111/3/?bust=1392319301&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28536111/3/?bust=1392319301&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28536111/3/?bust=1392319301&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28536111/3/?bust=1392319301&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28536111/3/?bust=1392319301&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Rottweiler\",\n" +
            "        \"Labrador Retriever\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98092\",\n" +
            "        \"city\": \"Auburn\",\n" +
            "        \"email\": \"pugetsoundrescue@hotmail.com\",\n" +
            "        \"state\": \"WA\",\n" +
            "        \"address1\": \"available upon request\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"altered\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA149\",\n" +
            "      \"description\": \"Kit is a very handsome rottweiler mix.  He's about 6 years old, bloodwork is fine, and vaccinations are up to date. Kit's crate-trained, obeys many commands, walks with a harness, wears a muzzle at the vets when necessary, is very treat motivated (therefore easy to train) and does best with less stimulation instead of more since he seems to think he has to keep it all under control. Having said that, he really just needs consistency, whether it's a 1 adult household or a 4 adult household. No kids, because he sees them as a threat. \\n\\nKit is well trained, loyal and not hard to manage if one is consistent about the rules. He is an excellent companion. He loves to chase balls and sticks and bring them back, he loves to sleep on the couch (doesn't require a lot of exercise), and gets along well with other dogs.\\n\\nKit would love to become part of your family, come see him today!\\n\\nKit's Adoption Donation : $200\\n\\nCONTACT US TODAY: Please fill out our short online adoption application at http://www.pugetsoundrescue.org/application.html before adopting any dog from Puget Sound Rescue.\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"28562473\",\n" +
            "      \"age\": \"Young\",\n" +
            "      \"mix\": \"no\",\n" +
            "      \"sex\": \"M\",\n" +
            "      \"name\": \"Sprocket\",\n" +
            "      \"size\": \"L\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28562473/1/?bust=1477450943&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28562473/1/?bust=1477450943&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28562473/1/?bust=1477450943&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28562473/1/?bust=1477450943&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28562473/1/?bust=1477450943&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28562473/2/?bust=1477450944&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28562473/2/?bust=1477450944&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28562473/2/?bust=1477450944&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28562473/2/?bust=1477450944&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28562473/2/?bust=1477450944&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"3\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28562473/3/?bust=1477450944&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28562473/3/?bust=1477450944&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28562473/3/?bust=1477450944&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28562473/3/?bust=1477450944&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28562473/3/?bust=1477450944&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Boxer\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"fax\": \"5094646053\",\n" +
            "        \"zip\": \"98122\",\n" +
            "        \"city\": \"Seattle\",\n" +
            "        \"email\": \"nwboxerrescue@hotmail.com\",\n" +
            "        \"phone\": \"5096074740\",\n" +
            "        \"state\": \"WA\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"altered\",\n" +
            "        \"noCats\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA546\",\n" +
            "      \"description\": \"~~Sprocket\\n 4.5 Year old Male\\n 65 pounds\\n Dog Friendly with very slow introductions (submissive/calm female)\\n Cats Unknown\\n Older Kids preferred\\n Fostered in Spanaway, WA\\n\\n Hello, My name is Sprocket and I am told that I am a very sweet loving boy! I am one of those guys that likes to have a fur sister around to make me feel safe and comfortable...oh! And to run zoomies with! New or unknown sounds, people, things and animals make me a little nervous, but with a strong and confident leader I become very curious and want to know what everything is.\\n\\n I have come a very long way with the constant guidance and encouragement from my Foster Mom. I was pretty anxious when I first arrived at NWBR and I had to be introduced slowly to my fur sister. But once I knew I was safe, she became my bestest bud and I do so much better in her company than I do on my own. I really love my foster sister, she really helps me feel much more confident.\\n\\n Now, it did take about a week for me to get used to my new home and Foster Family. Foster Mom did a lot of crate and rotate with the 2 of us, to make sure everyone was feeling safe. Then when I got to meet my foster sister with no barriers between us, I was very excited but no longer afraid. Within minutes we were running around the yard and having a blast. Running zoomies is one of our most favorite things to do.\\n\\n I am told that with my foster sister I have no problems sharing anything and everything. Many times throughout each day we will share and swap toys, bully sticks, water bowls even food dishes. I don't even mind when my Foster Mom puts her hands in my food bowl while I'm eating or even in my mouth while I'm trying to eat or have a treat. I even have great manners on quietly, patiently waiting to get fed. I now know that I have to be sitting and calm out of the kitchen, while foster mom prepares my food. Then I even wait until my foster sister is released to eat her food and then I get released to eat my food. I am learning that I don't have to inhale my food because there will always be more later! I even have some neat things I have learned like I do know how to sit, lay down, shake, stay, enough, don't touch, leave it, wait, come and I'm sure there's more that I know or am learning.\\n\\n I am housetrained and crate trained (though you might find me out of the crate when you get home if it's not secure). But I don't make a big mess if I escape. You'll probably want to make sure that garbage and food are put away though...because I am a dog! And I sure do love things that smell like they should be eaten! I am working on my leash training and doing pretty well, so we can go on lots of walks and excursions to help build my confidence, too.\\n I am very much wanting to please and make sure I am doing what it is you want me to do. I am told that I am a good boy and a fast learner...once I build confidence, I am a pleasure to have around and will be your best friend forever. I sure hope you'll consider me and fill out an adoption application over at: http://www.nwboxerrescue.org/#!adoption-form/c1dw9\\n\\n  If you would like to adopt him, fill out an adoption application on our website www.nwboxerrescue.org or at facebook.com/northwestboxerrescue Like us on facebook for more photos of this handsome guy, as well as other boxers we have available!\",\n" +
            "      \"shelterPetId\": \"WA546\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"dog\": {\n" +
            "      \"id\": \"28929272\",\n" +
            "      \"age\": \"Adult\",\n" +
            "      \"mix\": \"yes\",\n" +
            "      \"sex\": \"M\",\n" +
            "      \"name\": \"Leroy\",\n" +
            "      \"size\": \"L\",\n" +
            "      \"media\": {\n" +
            "        \"photos\": {\n" +
            "          \"1\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28929272/1/?bust=1460409799&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28929272/1/?bust=1460409799&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28929272/1/?bust=1460409799&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28929272/1/?bust=1460409799&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28929272/1/?bust=1460409799&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"2\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28929272/2/?bust=1460409799&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28929272/2/?bust=1460409799&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28929272/2/?bust=1460409799&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28929272/2/?bust=1460409799&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28929272/2/?bust=1460409799&width=60&-pnt.jpg\"\n" +
            "          },\n" +
            "          \"3\": {\n" +
            "            \"t\": \"http://photos.petfinder.com/photos/pets/28929272/3/?bust=1460409799&width=50&-t.jpg\",\n" +
            "            \"x\": \"http://photos.petfinder.com/photos/pets/28929272/3/?bust=1460409799&width=500&-x.jpg\",\n" +
            "            \"pn\": \"http://photos.petfinder.com/photos/pets/28929272/3/?bust=1460409799&width=300&-pn.jpg\",\n" +
            "            \"fpm\": \"http://photos.petfinder.com/photos/pets/28929272/3/?bust=1460409799&width=95&-fpm.jpg\",\n" +
            "            \"pnt\": \"http://photos.petfinder.com/photos/pets/28929272/3/?bust=1460409799&width=60&-pnt.jpg\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"animal\": \"Dog\",\n" +
            "      \"breeds\": [\n" +
            "        \"Pit Bull Terrier\"\n" +
            "      ],\n" +
            "      \"status\": \"A\",\n" +
            "      \"contact\": {\n" +
            "        \"zip\": \"98062\",\n" +
            "        \"city\": \"Seahurst\",\n" +
            "        \"email\": \"heather@myaarf.org\",\n" +
            "        \"phone\": \"2069101038\",\n" +
            "        \"state\": \"WA\",\n" +
            "        \"address1\": \"PO Box 328\"\n" +
            "      },\n" +
            "      \"options\": [\n" +
            "        \"hasShots\",\n" +
            "        \"noDogs\",\n" +
            "        \"altered\",\n" +
            "        \"noCats\",\n" +
            "        \"housetrained\"\n" +
            "      ],\n" +
            "      \"shelterId\": \"WA355\",\n" +
            "      \"description\": \"Leroy is a 9 year old Pit mix who is such a joy to be around! He loves all people and would love nothing more than to cuddle with you. He spent 5 years in a hoarding situation, so we are carefully looking for a home that will be dedicated to loving Leroy for the rest of his life (or a foster home to help while we wait for that home)! Leroy told us what he's looking for in a foster or a forever home, so we thought we'd just let him share it in his own words! \\\"It may sound a bit selfish on my part, but I am not a fan of other dogs or cats, I would like to shower you with attention all by myself! I would love someone that \\\"gets\\\" me - a dog savvy person is a must!\\\" I even have my own Facebook page - check me out! \\n\\nhttps://www.facebook.com/pages/Leroy/264220677068110 Leroy is crate-trained and potty-trained. $225 adoption fee applies. All of our dogs are spayed/neutered, vaccinated and microchipped. We do a 30-day trial adoption and if it's not a good fit during that period, we will refund 75% of the adoption fee. ***We frequently fall into spam folders, so if you haven't heard back from us in 24-48 hours, please check there***\"\n" +
            "    }\n" +
            "  }\n" +
            "]\n";

    private static final String TAG = DADAPI.class.getName();

    /*
    private static DADAPI instance = null;
    public static DADAPI getInstance() {
        if (instance == null) {
            instance = new DADAPI();
        }
        return instance;
    }
    */

    private static String PETFINDER_API_KEY = "d025e514d458e4366c42ea3006fd31b3";
    private static String PETFINDER_URL_BASE = "http://api.petfinder.com/pet.find&format=json&animal=dog&output=full&count=100?key=" + PETFINDER_API_KEY;
    private static String DAD_SERVER_URL_BASE = "http://ec2-35-160-226-75.us-west-2.compute.amazonaws.com/api/getNextDogs";

    private User user;
    private int zipCode;
    private int lastOffset;
    private URL petfinderURL;
    private Context context;

    public DADAPI(Context context) {
        this.context = context;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void seenDog(Dog dog) {
        JSONObject obj = new JSONObject();
        // execute method and handle any error responses.
    }

    private Set<Dog> filterSeenDogs(Set<Dog> result) {
        // backend.getSeenDogs(I) | filter result
        return null;
    }

    public void setLocation(int zipCode) {
        this.zipCode = zipCode;
        this.lastOffset = 0;

        try {
            petfinderURL = new URL(PETFINDER_URL_BASE + "&location=" + zipCode + "&offset=" + lastOffset);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void addDogsFromJSON(String data, Set<Dog> result) {
        try {
            JSONArray dogs = (JSONArray) new JSONTokener(BACKEND_DATA).nextValue();
            for (int i = 0; i < dogs.length(); i++) {
                result.add(new Dog((JSONObject) dogs.get(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Set<Dog> getNextDogs(int zipCode) {
        final Set<Dog> result = new LinkedHashSet<>();

        RequestQueue queue = Volley.newRequestQueue(context);

        String host = "http://ec2-35-160-226-75.us-west-2.compute.amazonaws.com";
        String url = host + "/api/getNextDogsDemo";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("Reponse = ", "It did something with response" + response.toString());
                addDogsFromJSON(response.toString(), result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null) {
                    Log.v("Null error", "it died with a null error");
                } else if (error.getMessage() != null) {
                    System.out.println("HELLO");
                    Log.v("Error message", error.getMessage());
                    System.out.println(error.getMessage());
                } else {
                    Log.v("All are null", "");
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        Singleton.getInstance(context).addToRequestQueue(jsObjRequest);

        return result;

        // return filterSeenDogs(result);
    }

    private void judgeDog(Dog dog, boolean like) {

    }

    private void createDogsListFromJSON(String json) {

    }

    public void likeDog(Dog dog) {

    }

    public void dislikeDog(Dog dog) {

    }

    public Set<DateRequest> getRequests() {
        return null;
    }

    public void requestDate(Dog dog, Form form) {

    }

}
