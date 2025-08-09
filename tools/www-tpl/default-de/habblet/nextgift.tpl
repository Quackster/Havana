					{% if newbieNextGift < 3 %}
					<div class="gift-img">
					  {% if newbieNextGift == 1 %}
                          <img src="{{ site.staticContentPath }}/web-gallery/v2/images/welcome/newbie_furni/noob_stool_{{ newbieRoomLayout }}.png" alt="Mein erster Stuhl">
					  {% elseif newbieNextGift == 2 %}
						  <img src="{{ site.staticContentPath }}/web-gallery/v2/images/welcome/newbie_furni/noob_plant.png" alt="Meine erste Pflanze">
					  {% endif %}
                    </div>
                    <div class="gift-content-container">
                      <p class="gift-content">
					  {% if newbieNextGift == 1 %}
                        Dein nächstes Gratis-Möbelstück ist <strong>Starterstuhl</strong>
					  {% elseif newbieNextGift == 2 %}
						Dein nächstes Gratis-Möbelstück ist <strong>Glücklicher Bambus</strong>
					  {% endif %}
					  </p>
                      <p>
                        <b>Zeit übrig:</b>
                        <span id="gift-countdown"></span>
                      </p>
                      <p class="last">
                        <a class="new-button green-button" href="{{ site.sitePath }}/client?forwardId=2&roomId={{ playerDetails.getSelectedRoomId() }}" target="client" onclick="HabboClient.roomForward(this, '{{ playerDetails.getSelectedRoomId() }}', 'private'); return false;"><b>Zu meinem Raum &gt;&gt;</b><i></i></a>
                      </p>
                      <br style="clear: both" />
                    </div>
                    <script type="text/javascript">
                      L10N.put('time.hours', '{0}h');
                      L10N.put('time.minutes', '{0}min');
                      L10N.put('time.seconds', '{0}s');
                      GiftQueueHabblet.init({{ newbieGiftSeconds }});
                    </script>
					{% else %}
					<p>
                      Wie bekommst du mehr Möbel in dein Zimmer?
                    </p>
                    <p>
                      Du kannst für nur 3 Credits ein Möbelset kaufen, welches eine Lampe, eine Matte und zwei Sessel umfasst. Cool oder?
                    </p>
                    <ul>
                      <li>1. Kaufe Münzen im <a href="{{ site.sitePath }}/credits">Münzbereich</a></li>
                      <li>2. Öffne den Katalog über die Hotel-Symbolleiste</li>
                      <li>3. Öffne den Deals Bereich</li>
                      <li>4. Wähle das Möbelset aus, das du möchtest</li>
                      <li>5. Danke für dein Einkauf :)</li>
                    </ul>
                    <p class="aftergift-img">
                      <img src="{{ site.staticContentPath }}/web-gallery/v2/images/giftqueue/aftergifts.png" alt="" width="289" height="63" />
                    </p>
                    <p class="last">
                      <a class="new-button green-button" href="{{ site.sitePath }}/client?forwardId=2&roomId={{ playerDetails.getSelectedRoomId() }}" target="client" onclick="HabboClient.roomForward(this, '{{ playerDetails.getSelectedRoomId() }}', 'private'); return false;"><b>Zu meinem Raum &gt;&gt;</b><i></i></a>
                    </p>
                    <script type="text/javascript">					  
                      HabboView.add(GiftQueueHabblet.initClosableHabblet);
                    </script>
					{% endif %}